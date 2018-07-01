package it.polimi.se2018.model;


import it.polimi.se2018.alternative_network.newserver.Server2;
import it.polimi.se2018.alternative_network.newserver.ServerController2;
import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.*;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.*;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.ColorNotRightException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.RoundTrackIndexException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;
import it.polimi.se2018.network.server.ServerController;

import java.util.LinkedList;


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
    private DiceColor colorRestriction;
    private ServerController server;
    private Server2 server2;


    public GameBoard(int number, ServerController setServer,Server2 setserver2) {

        stopGame = false;
        currentRound = 0;
        currentTurn = 1;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        factoryDiceForThisGame = new BalancedFactoryDice();// here for change the factory
        Deck deck = Deck.getDeck();
        player = new Player[number];
        server = setServer;
        server2 = setserver2;
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
    }

    public void notifyAllCards(int indexPlayer) {
        UpdateSinglePrivateObject packet = new UpdateSinglePrivateObject(indexPlayer, player[indexPlayer].getPrivateObject());
        packet.setPlayerId(indexPlayer);
        sendEventToView(packet);
        UpdateInitialWindowPatternCard packetWindows = new UpdateInitialWindowPatternCard(player[indexPlayer].getThe4WindowPattern());
        packetWindows.setPlayerId(indexPlayer);
        sendEventToView(packetWindows);
        UpdateAllToolCard packetTool = new UpdateAllToolCard(toolCard);
        packetTool.setPlayerId(indexPlayer);
        sendEventToView(packetTool);
        UpdateAllPublicObject packetObject = new UpdateAllPublicObject(objectivePublicCard);
        packetObject.setPlayerId(indexPlayer);
        sendEventToView(packetObject);
        UpdateInitDimRound packetRound = new UpdateInitDimRound(roundTrack);
        packetRound.setPlayerId(indexPlayer);
        sendEventToView(packetRound);
    }

    private void broadcast(EventView event) {
        for (int i = 0; i < player.length; i++) {
            event.setPlayerId(i);
            sendEventToView(event);
        }
    }

    private void sendEventToView(EventView event){
        if(server==null) server2.sendEventToView(event);
        else server.sendEventToView(event);
    };
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

//no setter

    //**************************************Observer/observable**************************************************


    //************************************class's method**********************************************
    //************************************class's method**********************************************
    //************************************class's method**********************************************
    private void setUpFirstRound() throws ValueDiceWrongException {
        //create the First Round
        stopGame = false;
        dicePool = new DiceStack();
        for (int i = 0; i < (2 * player.length + 1); i++) {
            dicePool.add(factoryDiceForThisGame.createDice());
        }
        updateDicePool();
    }

    private void freeHandPlayer(int indexPlayer) {
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

    private void updateTokenPoints(int indexPlayer) {
        UpdateSinglePlayerTokenAndPoints packet = new UpdateSinglePlayerTokenAndPoints(indexPlayer, player[indexPlayer].getFavorToken(), player[indexPlayer].getPoints());
        broadcast(packet);
    }

    /**
     * change the current player to the next and end the game.
     * check the state of the player for the first/second turn
     * move the dice in hand to the DicePool.
     *
     * @param indexPlayerEnded that request the move
     * @throws GameIsBlockedException  if the game can't be modified
     * @throws CurrentPlayerException  if the requester isn't the current player
     * @throws GameIsOverException     if the game is over
     * @throws FatalGameErrorException if the game is corrupted
     */
    public void nextPlayer(int indexPlayerEnded) throws GameIsBlockedException, CurrentPlayerException,
            GameIsOverException, FatalGameErrorException, ValueDiceWrongException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayerEnded != indexCurrentPlayer) throw new CurrentPlayerException();
        if (currentTurn < player.length) {
            //se arrivi dal primo turno il tuo prossimo è un secondo turno
            //se non arrivi dal primo turno il tuo prossimo è un primo turno
            if (player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(false);
            else player[indexPlayerEnded].endTurn(true);
            //   System.err.println("Round: "+currentRound+" ------- Turno :"+currentTurn+" ------- ha terminato il turno :"+indexCurrentPlayer);
            indexCurrentPlayer = (indexPlayerEnded + 1) % player.length;
            freeHandPlayer(indexPlayerEnded);
            currentTurn++;
            EventView infoTurn = new UpdateInfoCurrentTurn(currentRound + 1, currentTurn);
            broadcast(infoTurn);
            //se il prossimo giocatore non è nel primo allora passo al prossimo giocatore
            if (!player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn == player.length) {
            //se arrivi dal primo turno il tuo prossimo è un secondo turno
            //se non arrivi dal primo turno il tuo prossimo è un primo turno
            if (player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(false);
            else player[indexPlayerEnded].endTurn(true);
            //   System.err.println("Round: "+currentRound+" ------- Turno :"+currentTurn+" ------- ha terminato il turno :"+indexCurrentPlayer);
            freeHandPlayer(indexPlayerEnded);
            currentTurn++;
            EventView infoTurn = new UpdateInfoCurrentTurn(currentRound + 1, currentTurn);
            broadcast(infoTurn);
            //inizio del secondo giro se il giocatore deve fare il primo turno passa avanti
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn < (2 * player.length)) {
            //se arrivi dal secondo turno il tuo prossimo turno è un secondo
            //se non arrivi dal secondo turno il tuo prossimo turno è un primo
            if (!player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(true);
            else player[indexPlayerEnded].endTurn(true);
            //    System.err.println("Round: "+currentRound+" ------- Turno :"+currentTurn+" ------- ha terminato il turno :"+indexCurrentPlayer);
            indexCurrentPlayer = (indexCurrentPlayer - 1) % player.length;
            if (indexCurrentPlayer < 0) indexCurrentPlayer = player.length + indexCurrentPlayer;
            currentTurn++;
            freeHandPlayer(indexPlayerEnded);
            EventView infoTurn = new UpdateInfoCurrentTurn(currentRound + 1, currentTurn);
            broadcast(infoTurn);
            //se il giocatore deve fare il primo turno passa avanti
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn == (2 * player.length)) { //fine round
            //se arrivi dal secondo turno il tuo prossimo turno è un secondo
            //se non arrivi dal secondo turno il tuo prossimo turno è un primo
            if (!player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(true);
            else player[indexPlayerEnded].endTurn(true);
            //  System.err.println("Round: "+currentRound+" ------- Turno :"+currentTurn+" ------- ha terminato il turno :"+indexCurrentPlayer);
            freeHandPlayer(indexPlayerEnded);
            roundTrack[currentRound] = dicePool;
            UpdateSingleTurnRoundTrack packetRound = new UpdateSingleTurnRoundTrack(currentRound, roundTrack[currentRound]);
            broadcast(packetRound);
            currentRound++;
            if (currentRound < roundTrack.length) {//se non è finito il gioco
                indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
                currentTurn = 1;
                EventView infoTurn = new UpdateInfoCurrentTurn(currentRound + 1, currentTurn);
                broadcast(infoTurn);
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
                try{
                calculatePoint();
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.err.println("Il gioco è finito");
                throw new GameIsOverException();
            }
        } else {
            System.err.println("I turni sono impazziti.");
            stopGame = true;
            throw new FatalGameErrorException();
        }
        System.err.println("Round: " + currentRound + " ------- Turno :" + currentTurn + " ------- tocca a :" + indexCurrentPlayer);
    }

    private class NodePodium {
        private NodePodium father;
        private NodePodium leftLessPoint;
        private NodePodium rightMorePoint;
        private int indexPlayer;
        private int totalPoint;
        private LinkedList detailedPoints;

        private NodePodium(int indexPlayer, int totalPoint, LinkedList detailedPoints) {
            this.indexPlayer = indexPlayer;
            this.totalPoint = totalPoint;
            this.detailedPoints = detailedPoints;
        }
    }

    private class TreePodium {
        private NodePodium root;

        public NodePodium getRoot() {
            return root;
        }

        /**
         * a parità di punteggio con < quello inserito più tardi vince
         *                       con <= quello inserito per primo vince
         * @param newNodePlayer
         */
        void insertNodePlayer(NodePodium newNodePlayer) {
            NodePodium y = null;
            NodePodium x = root;
            while (x != null) {
                y = x;
                if (newNodePlayer.totalPoint <= x.totalPoint) x = x.leftLessPoint;
                else x = x.rightMorePoint;
            }
            newNodePlayer.father = y;
            if (y == null) this.root = newNodePlayer;
            else if (newNodePlayer.totalPoint <= y.totalPoint) y.leftLessPoint = newNodePlayer;
            else y.rightMorePoint = newNodePlayer;
        }

        NodePodium max(NodePodium node) {
            while (node.rightMorePoint != null) node = node.rightMorePoint;
            return node;
        }

        NodePodium predecessor(NodePodium nodePlayer) {
            if (nodePlayer.leftLessPoint != null) return max(nodePlayer.leftLessPoint);
            NodePodium y = nodePlayer.father;
            if (y != null) if (y.rightMorePoint == nodePlayer) return y;
            return null;
        }
        int[] returnDetailedArrayInt (NodePodium nodePlayer){
            int[] infoNode= new int[nodePlayer.detailedPoints.size()];
            for(int i=0; i<nodePlayer.detailedPoints.size();i++)  infoNode[i] = (int)nodePlayer.detailedPoints.get(i);
            return infoNode;
        }
    }

    private void calculatePoint() {
        TreePodium podium = new TreePodium();
        String[] description= new String[9];

        for (int i = 0; i < player.length; i++) {
            Player playerX = player[i];
            LinkedList<Integer> point = new LinkedList<>();
            //calculate point for the private object
            point.addLast(playerX.getPrivateObject().calculatePoint(playerX.getPlayerWindowPattern()));
            //calculate point for the public object
            for (ObjectivePublicCard anObjectivePublicCard : objectivePublicCard) {
                point.addLast(anObjectivePublicCard.calculatePoint(playerX.getPlayerWindowPattern()));
            }
            //add the token left
            point.addLast(playerX.getFavorToken());
            //subtract the void cell
            point.addLast((-20 + playerX.getPlayerWindowPattern().getNumberOfCellWithDice()));
            //sum of all the points
            int totalPoint = 0;
            for (Integer aPoint : point) totalPoint += aPoint;
            playerX.setPoints(totalPoint);
            playerX.setDetailedPoint(point);
            NodePodium newPlayerNode = new NodePodium(i, totalPoint, point);
            podium.insertNodePlayer(newPlayerNode);
        }
        //inolra a tutti il punteggio
        for (int i = 0; i < player.length; i++) {
            UpdateSinglePlayerTokenAndPoints packet = new UpdateSinglePlayerTokenAndPoints(i, player[i].getFavorToken(), player[i].getPoints());
            broadcast(packet);
        }
        //costruzione array ordinato
        NodePodium currentPlayerSorted = podium.max(podium.getRoot());
        int header =2;
        int[][] sortedPlayer = new int[player.length][currentPlayerSorted.detailedPoints.size()+header];
        for (int i = 0; i < player.length; i++) {
            sortedPlayer[i][0] = currentPlayerSorted.indexPlayer;
            sortedPlayer[i][1] = currentPlayerSorted.totalPoint;
            for(int j=header; j< (currentPlayerSorted.detailedPoints.size()+header);j++){
                sortedPlayer[i][j] = (int) currentPlayerSorted.detailedPoints.get(j-header);
            }
            currentPlayerSorted = podium.predecessor(currentPlayerSorted);
        }
        //descrizione informazione dell'array
        description[0] = "Nome Giocatore";
        description[1] = "Punti totali accumulati";
        description[2] = "Obiettivo Privato";
        description[3] = objectivePublicCard[0].getName();
        description[4] = objectivePublicCard[1].getName();
        description[5] = objectivePublicCard[2].getName();
        description[6] = "Token Rimasti";
        description[7] = "Celle Vuote";
        UpdateStatPodium packet = new UpdateStatPodium(sortedPlayer, description);
        broadcast(packet);
    }


    /**
     * move for select the window Pattern
     *
     * @param idPlayer         who want to set the window
     * @param indexOfTheWindow of the window selected
     */
    public void setWindowOfPlayer(int idPlayer, int indexOfTheWindow) throws WindowPatternAlreadyTakenException, WindowSettingCompleteException, ValueDiceWrongException {
        if (player[idPlayer].getPlayerWindowPattern() != null) throw new WindowPatternAlreadyTakenException();
        player[idPlayer].choosePlayerWindowPattern(indexOfTheWindow);
        for (int i = 0; i < player.length; i++) {
            UpdateSingleWindow packet = new UpdateSingleWindow(idPlayer, player[idPlayer].getPlayerWindowPattern());
            packet.setPlayerId(i);
            sendEventToView(packet);

        }
        updateTokenPoints(idPlayer);
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
        player[indexPlayer].addDiceToHand(dice, true);
        dicePool.remove(indexDicePool);
        updateHand(indexPlayer);
        updateDicePool();
    }

    /**
     * method for inser a normal die in the window
     *
     * @param indexPlayer
     * @param line
     * @param column
     * @param firstTurnDie
     * @throws GameException
     */
    public void insertDice(int indexPlayer, int line, int column, boolean firstTurnDie) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(line, column, true, true, true, firstTurnDie);
        player[indexPlayer].setHasPlaceANewDice(true);
        updateHand(indexPlayer);
        Cell cell = player[indexPlayer].getPlayerWindowPattern().getCell(line, column);
        UpdateSingleCell packetCell = new UpdateSingleCell(indexPlayer, line, column, cell.getDice(), cell.getValueRestriction(), cell.getColorRestriction());
        broadcast(packetCell);
    }


    public void useToolCard(int indexPlayer, int indexOfToolInGame) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (player[indexPlayer].isHasUsedToolCard()) throw new AlreadyUseToolCardException();
        toolCard[indexOfToolInGame].checkUsabilityToolCard(currentRound, player[indexPlayer]);
        player[indexPlayer].useToolCard(toolCard[indexOfToolInGame].getFavorToken());
        toolCard[indexOfToolInGame].incrementUsage();
        UpdateSinglePlayerTokenAndPoints packet = new UpdateSinglePlayerTokenAndPoints(indexPlayer, player[indexPlayer].getFavorToken(), player[indexPlayer].getPoints());
        broadcast(packet);
        UpdateSingleToolCardCost packet2 = new UpdateSingleToolCardCost(indexOfToolInGame, toolCard[indexOfToolInGame].getFavorToken());
        broadcast(packet2);
    }
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************
    //*****************************************Tool's method of Gameboard **********************************************

    /**
     * Method to select the restriction for remove the next dice form the window pattern
     *
     * @param indexPlayer
     * @param round
     * @param index
     * @throws GameException
     */
    public void imposeColorRestriction(int indexPlayer, int round, int index) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (round >= currentRound || round < 0) throw new RoundTrackIndexException();
        if (index < 0 || index >= roundTrack[round].size()) throw new NoDiceException();
        colorRestriction = roundTrack[round].getDice(index).getColor();
    }

    /**
     * method for remove a dice from the window pattern
     *
     * @param indexPlayer
     * @param line
     * @param column
     * @param checkRestriction if the color restriction need to be verified
     * @throws GameException
     */
    public void moveDiceFromWindowPatternToHand(int indexPlayer, int line, int column, boolean checkRestriction) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (checkRestriction && !colorRestriction.equals(player[indexPlayer].getPlayerWindowPattern().getCell(line, column).getDice().getColor()))
            throw new ColorNotRightException();
        player[indexPlayer].removeDiceFromWindowAndAddToHand(line, column);
        updateHand(indexPlayer);
        Cell cell = player[indexPlayer].getPlayerWindowPattern().getCell(line, column);
        UpdateSingleCell packetCell = new UpdateSingleCell(indexPlayer, line, column, cell.getDice(), cell.getValueRestriction(), cell.getColorRestriction());
        broadcast(packetCell);
    }


    /**
     * move for take the active dice in hand and change it with a new one
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return true if is gone all ok, false otherwise
     */
    public void changeDiceBetweenHandAndFactory(int indexPlayer) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        Dice dice = player[indexPlayer].removeDiceFromHand();
        factoryDiceForThisGame.removeDice(dice);
        dice = factoryDiceForThisGame.createDice();
        player[indexPlayer].addDiceToHand(dice, false);
        updateHand(indexPlayer);
    }

    /**
     * swap the die in hand and the roundtrack
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @param round
     * @param indexStack
     * @return
     */
    public void changeDiceBetweenHandAndRoundTrack(int indexPlayer, int round, int indexStack) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (round >= currentRound || round < 0)
            throw new RoundTrackIndexException();//can't select a round that didn't exist
        if (indexStack >= roundTrack[round].size() || indexStack < 0) throw new NoDiceException();
        Dice dicePlayer = player[indexPlayer].removeDiceFromHand();
        player[indexPlayer].addDiceToHand(roundTrack[round].get(indexStack), false);
        roundTrack[round].add(indexStack, dicePlayer);
        updateHand(indexPlayer);
        EventView packetCell = new UpdateSingleTurnRoundTrack(round, roundTrack[round]);
        broadcast(packetCell);
    }


    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void rollDicePool(int indexPlayer) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (currentTurn < player.length) throw new GameException("Non effettuare questa mossa adesso");
        dicePool.reRollAllDiceInStack();
        EventView packetCell = new UpdateDicePool(dicePool);
        broadcast(packetCell);
    }
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************

    /**
     * method for insert a dice in the window
     *
     * @param indexPlayer
     * @param line
     * @param column
     * @param adjacentRestriction
     * @param colorRestriction
     * @param valueRestriction
     * @param singleNewDice
     * @throws GameException
     */
    public void insertDice(int indexPlayer, int line, int column, boolean adjacentRestriction,
                           boolean colorRestriction, boolean valueRestriction, boolean singleNewDice) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        if (singleNewDice && player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(line, column, adjacentRestriction, colorRestriction, valueRestriction, singleNewDice);
        if (singleNewDice) player[indexPlayer].setHasPlaceANewDice(true);
        updateHand(indexPlayer);
        Cell cell = player[indexPlayer].getPlayerWindowPattern().getCell(line, column);
        UpdateSingleCell packetCell = new UpdateSingleCell(indexPlayer, line, column, cell.getDice(), cell.getValueRestriction(), cell.getColorRestriction());
        broadcast(packetCell);
    }

    /**
     * @param indexPlayer
     * @throws GameException
     */
    public void rollDiceInHand(int indexPlayer) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        player[indexPlayer].rollDiceInHand();
        updateHand(indexPlayer);
    }

    /**
     * @param indexPlayer
     * @throws GameException
     */
    public void oppositeFaceDice(int indexPlayer) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        player[indexPlayer].oppositeFaceDice();
        updateHand(indexPlayer);
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void endSpecialFirstTurn(int indexPlayer) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        player[indexCurrentPlayer].endSpecialFirstTurn();
        freeHandPlayer(indexPlayer);
    }

    public void setValueDiceHand(int indexPlayer, int value) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        player[indexPlayer].setValueDiceHand(value);
        updateHand(indexPlayer);
    }

    public void increaseOrDecrease(int indexPlayer, boolean increase) throws GameException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
        player[indexPlayer].increaseOrDecrease(increase);
        updateHand(indexPlayer);
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
    public void selectDiceInHand(int indexPlayer, int index) throws GameException {
     /*   try {
            if (stopGame) return false;// game stopped
            if (indexPlayer != indexCurrentPlayer) return false; //not your turn
            return player[indexCurrentPlayer].selectDiceInHand(indexPlayer);
        } catch (Exception e) {
            return false;
        }*/
    }


}
