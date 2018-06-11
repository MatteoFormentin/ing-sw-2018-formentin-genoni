package it.polimi.se2018.model;


import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.*;
import it.polimi.se2018.exception.player_exception.*;
import it.polimi.se2018.exception.window_exception.WindowRestriction;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;
import it.polimi.se2018.network.server.ServerController;


/**
 * the game board for one game with all the method that can be used in a game
 *
 * @author Luca Genoni
 */
public class GameBoard {
    private int currentRound;//can have only the get
    private int currentTurn;//can have only the get
    private int indexCurrentPlayer;//can have only the get
    private boolean stopGame;//can have only the get
    private int countSetWindow;
    private DiceStack[] roundTrack;//can have only the get
    private Player[] player; //can have only the get(they are created with the constructor )
    private ToolCard[] toolCard;//can have only the get
    private ObjectivePublicCard[] objectivePublicCard;//can have only the get
    private DiceStack dicePool;//can have only the get
    private FactoryDice factoryDiceForThisGame; //nobody can see it
    private ServerController server;


    public GameBoard(int number, ServerController setServer) {

        stopGame = true;
        currentRound = 0;
        currentTurn = 1;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        factoryDiceForThisGame = new BalancedFactoryDice();// here for change the factory
        Deck deck = Deck.getDeck();
        player = new Player[number];
        server = setServer;
        //setUp player
        for (int i = 0; i < number; i++) {
            player[i] = new Player(i);
            player[i].setPrivateObject(deck.drawObjectivePrivateCard());
            WindowPatternCard[] window = new WindowPatternCard[4];
            for (int n = 0; n < 4; n++) {
                window[n] = deck.drawWindowPatternCard();
            }
            player[i].setThe4WindowPattern(window);
        }
        indexCurrentPlayer = 0;
        for (int i = 0; i < toolCard.length; i++) toolCard[i] = deck.drawToolCard();

        for (int i = 0; i < objectivePublicCard.length; i++) objectivePublicCard[i] = deck.drawObjectivePublicCard();

        System.err.println("Gameboard Creata con " + number + " giocatori");
    }

    public void notifyAllCards(int indexPlayer) {
        UpdateSinglePrivateObject packet = new UpdateSinglePrivateObject(indexPlayer, player[indexPlayer].getPrivateObject());
        packet.setPlayerId(indexPlayer);
        server.sendEventToView(packet);
        UpdateInitialWindowPatternCard packetWindows = new UpdateInitialWindowPatternCard(player[indexPlayer].getThe4WindowPattern());
        packetWindows.setPlayerId(indexPlayer);
        server.sendEventToView(packetWindows);
        UpdateAllToolCard packetTool = new UpdateAllToolCard(toolCard);
        packetTool.setPlayerId(indexPlayer);
        server.sendEventToView(packetTool);
        UpdateAllPublicObject packetObject = new UpdateAllPublicObject(objectivePublicCard);
        packetObject.setPlayerId(indexPlayer);
        server.sendEventToView(packetObject);
        UpdateInitDimRound packetRound = new UpdateInitDimRound(roundTrack);
        packetRound.setPlayerId(indexPlayer);
        server.sendEventToView(packetRound);
        System.err.println("inviati tutti le info iniziali a " + indexPlayer);
    }

    private void broadcast(EventView event) {
        for (int i = 0; i < player.length; i++) {
            event.setPlayerId(i);
            server.sendEventToView(event);
        }
    }
    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    /**
     * @return int for the current round
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * @return the array of DiceStack belonging to the roundtrack
     */
    public DiceStack[] getRoundTrack() {
        return roundTrack;
    }

    /**
     * @return array of all the Player
     */
    public Player[] getPlayer() {
        return player;
    }

    /**
     * @param index of the player domain [0, n°player-1]
     * @return the player relative to the index, null if the index is wrong
     */
    public Player getPlayer(int index) {
        if (index < 0 || index >= player.length) return null;
        return player[index];
    }

    /**
     * @return an array of Tool Card
     */
    public ToolCard[] getToolCard() {
        return toolCard;
    }

    /**
     * @param index of the Tool card domain [0,2]
     * @return the ToolCard relative to the index, null if the index is wrong
     */
    public ToolCard getToolCard(int index) {
        if (index < 0 || index > toolCard.length) return null;
        return toolCard[index];
    }

    /**
     * @param index of the Tool card domain [0,2]
     * @return the ID of the ToolCard in position of the param index, -1 if index is wrong
     */
    public int getIdToolCard(int index) {
        if (index < 0 || index >= toolCard.length) return -1;
        return toolCard[index].getId();
    }

    /**
     * @return an array of ObjectivePublicCard
     */
    public ObjectivePublicCard[] getObjectivePublicCard() {
        return objectivePublicCard;
    }

    /**
     * @return a DiceStack relative to the DicePool
     */
    public DiceStack getDicePool() {
        return dicePool;
    }

    /**
     * @return the index relative to the current player [0, n°player-1]
     */
    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    /**
     * @return the number of the current round
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @return true if the game over, false otherwise
     */
    public boolean isStopGame() {
        return stopGame;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************

    public void setServer(ServerController server) {
        this.server = server;
    }

    //no setter

    //**************************************Observer/observable**************************************************


    //************************************class's method**********************************************
    //************************************class's method**********************************************
    //************************************class's method**********************************************
    private void setUpFirstRound() {
        //create the First Round
        stopGame = false;
        dicePool = new DiceStack();
        for (int i = 0; i < (2 * player.length + 1); i++) {
            dicePool.add(factoryDiceForThisGame.createDice());
        }
        updateDicePool();
    }

    private void freeHandPlayer(int indexPlayer) {
        /*
        while (player[indexPlayer].getHandDice().size() != 0) {
            dicePool.addLast(player[indexPlayer].getHandDice().remove(0));
        }
         */
        while (!player[indexPlayer].getHandDice().isEmpty()) {
            dicePool.addLast(player[indexPlayer].getHandDice().remove(0));
        }
        updateHand(indexPlayer);
        updateDicePool();
    }

    private void updateHand(int indexPlayer) {
        UpdateSinglePlayerHand packet = new UpdateSinglePlayerHand(indexPlayer, player[indexPlayer].getHandDice());
        broadcast(packet);
    }

    private void updateDicePool() {
        UpdateDicePool packet = new UpdateDicePool(dicePool);
        broadcast(packet);
    }

    /**
     * change the current player to the next and end the game.
     * check the state of the player for the first/second turn
     * move the dice in hand to the DicePool.
     *
     * @param indexPlayer that request the move
     * @throws GameIsBlockedException  if the game can't be modified
     * @throws CurrentPlayerException  if the requester isn't the current player
     * @throws GameIsOverException     if the game is over
     * @throws FatalGameErrorException if the game is corrupted
     */
    public void nextPlayer(int indexPlayer) throws GameIsBlockedException, CurrentPlayerException,
            GameIsOverException, FatalGameErrorException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (currentTurn < player.length) {
            if (player[indexPlayer].isFirstTurn()) {
                player[indexPlayer].endTrun(false);
            }//else he use a tool card that alter the normal circle
            freeHandPlayer(indexPlayer);

            roundTrack[currentRound] = dicePool;
            indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
            currentTurn++;
            if (!player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
            //se è uguale siamo nella fare tra il 1° e il 2° giro
        } else if (currentTurn == player.length) {
            if (player[indexPlayer].isFirstTurn()) {
                player[indexPlayer].endTrun(false);
            }//else he use a tool card that alter the normal circle
            freeHandPlayer(indexPlayer);

            currentTurn++;
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
            //siamo in pieno 2°giro
        } else if (currentTurn < (2 * player.length)) {
            if (!player[indexPlayer].isFirstTurn()) {
                player[indexPlayer].endTrun(true);
            }//else he use a tool card that alter the normal circle
            freeHandPlayer(indexPlayer);

            indexCurrentPlayer = (indexCurrentPlayer - 1) % player.length;
            if (indexCurrentPlayer < 0) indexCurrentPlayer = player.length + indexCurrentPlayer;
            currentTurn++;
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
            //siamo tra il 2° e il 1° giro/endgame
        } else if (currentTurn == (2 * player.length)) { //fine round
            if (!player[indexPlayer].isFirstTurn()) {
                player[indexPlayer].endTrun(true);
            }//else he use a tool card that alter the normal circle
            freeHandPlayer(indexPlayer);//rimette dadi rimanenti in mano nella draftpool
            roundTrack[currentRound] = dicePool;
            UpdateSingleTurnRoundTrack packetRound = new UpdateSingleTurnRoundTrack(currentRound, roundTrack[currentRound]);
            broadcast(packetRound);
            currentRound++;
            if (currentRound < roundTrack.length) {//se non è finito il gioco
                indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
                currentTurn = 1;
                dicePool = new DiceStack();
                for (int i = 0; i < (2 * player.length + 1); i++) {
                    Dice dice = factoryDiceForThisGame.createDice();
                    if (dice == null) {
                        throw new FatalGameErrorException();
                    }
                    dicePool.add(dice);
                }
                updateDicePool();
                if (!player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
            } else {//il gioco è finito
                stopGame = true;
                currentTurn = 2 * player.length + 1;
                //method for the end game
                calculatePoint();
                System.err.println("Il gioco è finito");
                throw new GameIsOverException();
            }
        } else {
            System.err.println("I turni sono impazziti.");
            throw new FatalGameErrorException();
        }
    }

    private void calculatePoint() {
        int pointCounter;
        for (Player playerX : this.player) {
            pointCounter = 0;
            //calculate point for the public object
            for (ObjectivePublicCard objectivePublicCardx : this.objectivePublicCard) {
                pointCounter += objectivePublicCardx.calculatePoint(playerX.getPlayerWindowPattern());
            }
            //calculate point for the private object
            pointCounter += playerX.getPrivateObject().calculatePoint(playerX.getPlayerWindowPattern());
            //add the token left
            pointCounter += playerX.getFavorToken();
            playerX.setFavorToken(0);
            //subtract the void cell
            pointCounter = pointCounter - (20 - playerX.getPlayerWindowPattern().getNumberOfCellWithDice());
            playerX.setPoints(pointCounter);
        }
        //inolra a tutti il punteggio
        for (int i = 0; i < player.length; i++) {
            UpdateSinglePlayerTokenAndPoints packet = new UpdateSinglePlayerTokenAndPoints(i, player[i].getFavorToken(), player[i].getPoints());
            broadcast(packet);
        }

    }

    /**
     * move for select the window Pattern
     *
     * @param idPlayer         who want to set the window
     * @param indexOfTheWindow of the window selected
     */
    public void setWindowOfPlayer(int idPlayer, int indexOfTheWindow) throws WindowPatternAlreadyTakenException, WindowSettingCompleteException {
        if (player[idPlayer].getPlayerWindowPattern() != null) throw new WindowPatternAlreadyTakenException();
        player[idPlayer].choosePlayerWindowPattern(indexOfTheWindow);
        for (int i = 0; i < player.length; i++) {
            UpdateSingleWindow packet = new UpdateSingleWindow(idPlayer, player[idPlayer].getPlayerWindowPattern());
            packet.setPlayerId(i);
            server.sendEventToView(packet);
            UpdateSinglePlayerTokenAndPoints packetToken = new UpdateSinglePlayerTokenAndPoints(idPlayer,
                    player[idPlayer].getFavorToken(), player[idPlayer].getPoints());
            packetToken.setPlayerId(idPlayer);
            server.sendEventToView(packetToken);
        }
        countSetWindow++;
        if (countSetWindow == player.length) {
            setUpFirstRound();
            throw new WindowSettingCompleteException();
        }
    }

//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************
//*****************************************metodi del player************************************************************************

    /**
     * @param indexPlayer   index of the player that request the move
     * @param indexDicePool index of the dice chosen
     * @throws NoDiceException there is no dice in the selected position
     */
    public void addNewDiceToHandFromDicePool(int indexPlayer, int indexDicePool) throws NoDiceException, GameIsBlockedException,
            CurrentPlayerException, AlreadyDrawANewDiceException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (player[indexPlayer].isHasDrawNewDice()) throw new AlreadyDrawANewDiceException();
        Dice dice = dicePool.getDice(indexDicePool);
        if (dice == null) throw new NoDiceException();
        player[indexPlayer].addDiceToHand(dice);
        dicePool.remove(indexDicePool);
        player[indexPlayer].setHasDrawNewDice(true);
        updateHand(indexPlayer);
        updateDicePool();
    }

    /**
     * method for insert the a new Dice in the Window Pattern
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param line
     * @param column
     * @return
     */
    public void insertDice(int indexPlayer, int line, int column) throws WindowRestriction,PlayerException, GameIsBlockedException,CurrentPlayerException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(line, column,true);
        player[indexPlayer].setHasPlaceANewDice(true);
        updateHand(indexPlayer);
        UpdateSingleCell packetCell = new UpdateSingleCell(indexPlayer, line, column, player[indexPlayer].getPlayerWindowPattern().getCell(line, column).getDice());
        broadcast(packetCell);
    }


    public void useToolCard(int indexPlayer, int indexOfToolInGame) throws Exception {
        throw new UnsupportedOperationException();
    /*
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (cost < 0) throw new NegativeCostException();
        player[indexPlayer].useToolCard(cost);*/

    }
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************

    /**
     * move for take the active dice in hand and change it with a new one
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return true if is gone all ok, false otherwise
     */
    public void changeDiceBetweenHandAndFactory(int indexPlayer) throws Exception {
/*
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (!player[indexPlayer].isHasUsedToolCard()) throw new NoToolCardInUseException();
        if (!player[indexPlayer].isHasDrawNewDice()) throw new NoDiceException();
        if (player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        Dice dice = player[indexPlayer].removeDiceFromHand();
        if (dice == null) throw new NoDiceException();
        factoryDiceForThisGame.removeDice(dice);
        dice = factoryDiceForThisGame.createDice();
        player[indexPlayer].addDiceToHand(dice);*/
    }

    /**
     * swap the die in hand and the roundtrack
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param round
     * @param indexStack
     * @return
     */
    public void changeDiceBetweenHandAndRoundTrack(int indexPlayer, int round, int indexStack) throws Exception {
       /*
            if (stopGame) throw new GameIsBlockedException();
            if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
            if (!player[indexPlayer].isHasUsedToolCard()) throw new NoToolCardInUseException();
           if (round >= currentRound || round < 0) throw new NoDiceStackException();//can't select a round that didn't exist
            if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
            if (!player[indexPlayer].isHasDrawNewDice()) throw new NoDiceException();
            if (player[indexPlayer].isHasPlaceANewDice()) return false;
            Dice dice = player[indexPlayer].removeDiceFromHand();
            if (dice == null) return false; // no dice in hand wtf
            player[indexPlayer].addDiceToHand(roundTrack[round].get(indexStack));
            roundTrack[round].add(indexStack, dice);*/

    }

    /**
     * remove the dice from the window pattern, but the dice need to be of the same color of the die selected in the roundTrack
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param round
     * @param indexStack
     * @param line
     * @param column
     * @return
     */
    public void moveDiceFromWindowPatternToHandWithRestriction(int indexPlayer, int round, int indexStack,
                                                               int line, int column) throws GameException {
       /* try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (round >= currentRound || round < 0) return false;//can't select a round that didn't exist
            if (indexStack >= roundTrack[round].size() || indexStack < 0) return false;// index wrong
            if (!player[indexPlayer].isHasUsedToolCard()) return false;//you didn't use a tool card
            Dice dHand = player[indexPlayer].getPlayerWindowPattern().getDice(line, column).getDice();
            if (dHand == null) return false;   //no dice in this cell
            if (dHand.getColor() != roundTrack[round].get(indexStack).getColor()) return false; // color isn't the same
            return player[indexPlayer].moveDiceFromWindowPatternToHand(line, column);
        } catch (Exception e) {
            return false;
        }*/
    }


    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void rollDicePool(int indexPlayer) throws GameException {
       /* try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            if (player[indexPlayer].isFirstTurn()) return false;
            dicePool.reRollAllDiceInStack();
            return true;
        } catch (Exception e) {
            return false;
        }*/

    }
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************

    /**
     * @param indexPlayer         who send the request of the move,(it should be the current player)
     * @param line
     * @param column
     * @param adjacentRestriction
     * @param colorRestriction
     * @param valueRestriction
     * @return
     */
    public void insertDice(int indexPlayer, int line, int column, boolean adjacentRestriction,
                           boolean colorRestriction, boolean valueRestriction, boolean singleNewDice) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (singleNewDice && player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(line, column, adjacentRestriction, colorRestriction, valueRestriction,true);
        if (singleNewDice) player[indexPlayer].setHasPlaceANewDice(true);
        updateHand(indexPlayer);
        UpdateSingleCell packetCell = new UpdateSingleCell(indexPlayer, line, column, player[indexPlayer].getPlayerWindowPattern().getCell(line, column).getDice());
        broadcast(packetCell);
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param line
     * @param column
     * @return
     */
    public void moveDiceFromWindowPatternToHand(int indexPlayer, int line, int column) throws GameException {
      /*  try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexPlayer].moveDiceFromWindowPatternToHand(line, column);
        } catch (Exception e) {
            return false;
        }*/
    }

    /**
     * method for move a dice(already placed one time in the window) from hand to window pattern
     *
     * @param indexPlayer
     * @param line
     * @param column
     * @param adjacentRestriction
     * @param colorRestriction
     * @param valueRestriction
     * @return
     */
 /*   public void moveOldDiceFromHandToWindowPattern(int indexPlayer, int line, int column, boolean adjacentRestriction, boolean colorRestriction, boolean valueRestriction) {
        try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false;//not your turn
            return player[indexPlayer].insertDice(line, column, adjacentRestriction, colorRestriction, valueRestriction);
        } catch (Exception e) {
            return false;
        }
    }*/

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void rollDiceInHand(int indexPlayer) throws Exception {
       /* try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexPlayer].rollDiceInHand();
        } catch (Exception e) {
            return false;
        }*/
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param increase    if true increase by 1, if false decrease by 1 the value of the active dice in hand (index 0)
     * @return
     */
    public void increaseOrDecrease(int indexPlayer, boolean increase) throws Exception {
      /*  try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].increaseOrDecrease(increase);
        } catch (Exception e) {
            return false;
        }*/
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void oppositeFaceDice(int indexPlayer) throws Exception {
      /*  try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].oppositeFaceDice();
        } catch (Exception e) {
            return false;
        }*/
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void endSpecialFirstTurn(int indexPlayer) throws Exception {
       /* try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].endSpecialFirstTurn();
        } catch (Exception e) {
            return false;
        }*/
    }

    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param index       of the die in hand
     * @return
     */
    public void selectDiceInHand(int indexPlayer, int index) throws Exception {
     /*   try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].selectDiceInHand(indexPlayer);
        } catch (Exception e) {
            return false;
        }*/
    }


}
