package it.polimi.se2018.model;


import it.polimi.se2018.exception.GameException;
import it.polimi.se2018.exception.gameboard_exception.*;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyDrawANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyUseToolCardException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.NoDiceInHandException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.ColorNotRightException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.RoundTrackIndexException;
import it.polimi.se2018.exception.gameboard_exception.tool_exception.ValueDiceWrongException;
import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.*;

/**
 * the class {@code GameBoard} represents the game board for a game that contains all the information of the game.
 * Any variation must go through this class to be sure of the reliability of the game data
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
    private UpdaterView updaterView;



    public GameBoard(String[] names) {
        stopGame = false;
        currentRound = 0;
        currentTurn = 1;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        player = new Player[names.length];
        for (int i = 0; i < names.length; i++) {
            System.out.println("name player stored: "+names[i]);
            player[i] = new Player(i,names[i]);
        }
        indexCurrentPlayer = 0;
    }

    public void startGame(UpdaterView updaterView) {
        this.updaterView = updaterView;
        Deck deck = Deck.getDeck();
        //setUp player
        int diceWindow = 0;
        for (int i = 0; i < player.length; i++) {
            player[i].setPrivateObject(deck.drawObjectivePrivateCard());
            WindowPatternCard[] window = new WindowPatternCard[4];
            for (int n = 0; n < 4; n++) window[n] = deck.drawWindowPatternCard();
            player[i].setThe4WindowPattern(window);
            if (diceWindow == 0 && window[0] != null) {
                diceWindow = window[0].getMatrix().length;
                if (diceWindow > 0) diceWindow = diceWindow * window[0].getColumn(0).length;
            }
        }
        for (int i = 0; i < toolCard.length; i++) toolCard[i] = deck.drawToolCard();
        for (int i = 0; i < objectivePublicCard.length; i++) objectivePublicCard[i] = deck.drawObjectivePublicCard();
        factoryDiceForThisGame = new BalancedFactoryDice(player.length, diceWindow,roundTrack.length);// here for change the factory
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

    public DiceStack getRoundTrack(int index) {
        return roundTrack[index];
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
        updaterView.updateDicePool();
    }

    private void freeHandPlayer(int indexPlayer) {
        while (!player[indexPlayer].getHandDice().isEmpty()) {
            dicePool.addLast(player[indexPlayer].getHandDice().remove(0));
        }
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateDicePool();
        updaterView.updateInfoCurrentTurn();
    }


    private void firstTurn(int indexPlayerEnded) {
        if (player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(false);
        else player[indexPlayerEnded].endTurn(true);
        freeHandPlayer(indexPlayerEnded);
        currentTurn++;
    }

    private void secondTurn(int indexPlayerEnded) {
        if (!player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(true);
        else player[indexPlayerEnded].endTurn(true);
        freeHandPlayer(indexPlayerEnded);
        currentTurn++;
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
            firstTurn(indexPlayerEnded);
            indexCurrentPlayer = (indexPlayerEnded + 1) % player.length;
            if (!player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn == player.length) {
            firstTurn(indexPlayerEnded);
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn < (2 * player.length)) {
            secondTurn(indexPlayerEnded);
            indexCurrentPlayer = (indexCurrentPlayer - 1) % player.length;
            if (indexCurrentPlayer < 0) indexCurrentPlayer = player.length + indexCurrentPlayer;
            if (player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
        } else if (currentTurn == (2 * player.length)) { //fine round
            secondTurn(indexPlayerEnded);
            roundTrack[currentRound] = dicePool;
            updaterView.updateSingleTurnRoundTrack(currentRound);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updaterView.currentPoints();
                }
            });
            currentRound++;
            if (currentRound < roundTrack.length) {//se non è finito il gioco
                indexCurrentPlayer = (indexCurrentPlayer + 1) % player.length;
                currentTurn = 1;
                updaterView.updateInfoCurrentTurn();
                dicePool = new DiceStack();
                for (int i = 0; i < (2 * player.length + 1); i++) {
                    Dice dice = factoryDiceForThisGame.createDice();
                    if (dice == null) {
                        throw new FatalGameErrorException();
                    }
                    dicePool.add(dice);
                }
                updaterView.updateDicePool();
                if (!player[indexCurrentPlayer].isFirstTurn()) nextPlayer(indexCurrentPlayer);
            } else {//il gioco è finito
                stopGame = true;
                currentTurn = 2 * player.length + 1;
                //method for the end game
                calculateAllPoint();

                throw new GameIsOverException();
            }
        } else {
            stopGame = true;
            throw new FatalGameErrorException();
        }
    }

    public NodePodium calculatePoint(int indexPlayer){
        NodePodium newPlayerNode = new NodePodium(player[indexPlayer],objectivePublicCard);
        newPlayerNode.calculatePoint();
        return newPlayerNode;
    }

    public void calculateAllPoint() {
        TreePodium podium = new TreePodium(player.length, roundTrack.length);
        for (Player aPlayer : player) {
            NodePodium newPlayerNode = new NodePodium(aPlayer, objectivePublicCard);
            podium.insertNodePlayer(newPlayerNode);
        }
        updaterView.updatePlayerTokenAndPoints();
        updaterView.updateStatPodium(podium.getSortedPlayer(), podium.getRoot().getDescription());
    }


    public void setWindowOfPlayer(int indexPlayer, int indexOfTheWindow) throws WindowPatternAlreadyTakenException, WindowSettingCompleteException, ValueDiceWrongException {
        if (player[indexPlayer].getPlayerWindowPattern() != null) throw new WindowPatternAlreadyTakenException();
        player[indexPlayer].choosePlayerWindowPattern(indexOfTheWindow);
        countSetWindow++;
        updaterView.updateWindow(indexPlayer);
        updaterView.updatePlayerTokenAndPoints(indexPlayer);
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
        checkState(indexPlayer);
        if (player[indexPlayer].isHasDrawNewDice()) throw new AlreadyDrawANewDiceException();
        Dice dice = dicePool.getDice(indexDicePool);
        if (dice == null) throw new NoDiceException();
        player[indexPlayer].addDiceToHand(dice, true);
        dicePool.remove(indexDicePool);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateDicePool();
    }

    public void setStopGame(boolean stopGame) {
        this.stopGame = stopGame;
    }

    /**
     * method for inser a normal die in the window
     *
     * @param indexPlayer
     * @param row
     * @param column
     * @param firstTurnDie
     * @throws GameException
     */
    public void insertDice(int indexPlayer, int row, int column, boolean firstTurnDie) throws GameException {
        checkState(indexPlayer);
        if (player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(row, column, true, true, true, firstTurnDie);
        player[indexPlayer].setHasPlaceANewDice(true);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateSingleCell(indexPlayer, row, column);
    }


    public void useToolCard(int indexPlayer, int indexOfToolInGame) throws GameException {
        checkState(indexPlayer);
        if (player[indexPlayer].isHasUsedToolCard()) throw new AlreadyUseToolCardException();
        toolCard[indexOfToolInGame].checkUsabilityToolCard(currentRound, player[indexPlayer]);
        player[indexPlayer].useToolCard(toolCard[indexOfToolInGame].getFavorToken());
        toolCard[indexOfToolInGame].incrementUsage();
        updaterView.updatePlayerTokenAndPoints(indexPlayer);
        updaterView.updateToolCardCost(indexOfToolInGame);
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
        checkState(indexPlayer);
        if (round >= currentRound || round < 0) throw new RoundTrackIndexException();
        if (index < 0 || index >= roundTrack[round].size()) throw new NoDiceException();
        colorRestriction = roundTrack[round].getDice(index).getColor();
    }

    /**
     * method for remove a dice from the window pattern
     *
     * @param indexPlayer
     * @param row
     * @param column
     * @param checkRestriction if the color restriction need to be verified
     * @throws GameException
     */
    public void moveDiceFromWindowPatternToHand(int indexPlayer, int row, int column, boolean checkRestriction) throws GameException {
        checkState(indexPlayer);
        if (checkRestriction && !colorRestriction.equals(player[indexPlayer].getPlayerWindowPattern().getCell(row, column).getDice().getColor()))
            throw new ColorNotRightException();
        player[indexPlayer].removeDiceFromWindowAndAddToHand(row, column);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateSingleCell(indexPlayer, row, column);
    }


    /**
     * move for take the active dice in hand and change it with a new one
     *
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return true if is gone all ok, false otherwise
     */
    public void changeDiceBetweenHandAndFactory(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        Dice dice = player[indexPlayer].removeDiceFromHand();
        factoryDiceForThisGame.removeDice(dice);
        dice = factoryDiceForThisGame.createDice();
        player[indexPlayer].addDiceToHand(dice, false);
        updaterView.updatePlayerHand(indexPlayer);
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
        checkState(indexPlayer);
        if (round >= currentRound || round < 0)
            throw new RoundTrackIndexException();//can't select a round that didn't exist
        if (indexStack >= roundTrack[round].size() || indexStack < 0) throw new NoDiceException();
        Dice dicePlayer = player[indexPlayer].removeDiceFromHand();
        player[indexPlayer].addDiceToHand(roundTrack[round].get(indexStack), false);
        roundTrack[round].add(indexStack, dicePlayer);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateSingleTurnRoundTrack(round);
    }


    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void rollDicePool(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        if (currentTurn < player.length) throw new GameException("Non effettuare questa mossa adesso");
        dicePool.reRollAllDiceInStack();
        updaterView.updateDicePool();
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
     * @param row
     * @param column
     * @param adjacentRestriction
     * @param colorRestriction
     * @param valueRestriction
     * @param singleNewDice
     * @throws GameException
     */
    public void insertDice(int indexPlayer, int row, int column, boolean adjacentRestriction,
                           boolean colorRestriction, boolean valueRestriction, boolean singleNewDice) throws GameException {
        checkState(indexPlayer);
        if (singleNewDice && player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(row, column, adjacentRestriction, colorRestriction, valueRestriction, singleNewDice);
        if (singleNewDice) player[indexPlayer].setHasPlaceANewDice(true);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateSingleCell(indexPlayer, row, column);
    }

    /**
     * @param indexPlayer
     * @throws GameException
     */
    public void rollDiceInHand(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].rollDiceInHand();
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * @param indexPlayer
     * @throws GameException
     */
    public void oppositeFaceDice(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].oppositeFaceDice();
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * @param indexPlayer who send the request of the move,(it should be the current player)
     * @return
     */
    public void endSpecialFirstTurn(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexCurrentPlayer].endSpecialFirstTurn();
        freeHandPlayer(indexPlayer);
    }

    public void setValueDiceHand(int indexPlayer, int value) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].setValueDiceHand(value);
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * @param indexPlayer integer that indicate which player send the request of
     * @param increase    true of increase the value false for decrease
     * @throws GameIsBlockedException  if the game can't be access
     * @throws CurrentPlayerException  if it isn't the player's turn
     * @throws ValueDiceWrongException if the request doesn't respect the dice domain
     * @throws NoDiceInHandException   if the player have no dice in hand
     * @throws NoDiceException
     */
    public void increaseOrDecrease(int indexPlayer, boolean increase) throws GameIsBlockedException,
            CurrentPlayerException, ValueDiceWrongException, NoDiceInHandException, NoDiceException {
        checkState(indexPlayer);
        player[indexPlayer].increaseOrDecrease(increase);
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * check the state of the game board and if it's the player turn
     *
     * @param indexPlayer integer that indicate which player send the request of
     * @throws GameIsBlockedException if the game can't be access
     * @throws CurrentPlayerException if it isn't the player's turn
     */
    private void checkState(int indexPlayer) throws GameIsBlockedException, CurrentPlayerException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
    }
}
