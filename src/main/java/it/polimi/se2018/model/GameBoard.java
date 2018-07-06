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
 * Class that represents the game board for a game that contains all the information of the game.
 * Any variation must go through this class to be sure of the reliability of the game data.
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

    /**
     * Constructor for the gameboard.
     *
     * @param names names of the player on the game.
     */
    public GameBoard(String[] names) {
        stopGame = false;
        currentRound = 0;
        currentTurn = 1;
        roundTrack = new DiceStack[10];// don't need to be initialized, they take the reference of from the dicePool
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];
        player = new Player[names.length];
        for (int i = 0; i < names.length; i++) {
            player[i] = new Player(i, names[i]);
        }
        indexCurrentPlayer = 0;
    }

    /**
     * Starter for the game.
     *
     * @param updaterView packet that contain an update for the view.
     */
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
        factoryDiceForThisGame = new BalancedFactoryDice(player.length, diceWindow, roundTrack.length);// here for change the factory
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the current round.
     *
     * @return int for the current round.
     */
    public int getCurrentRound() {
        return currentRound;
    }


    /**
     * Getter for the round track.
     *
     * @return the array of DiceStack belonging to the roundtrack.
     */
    public DiceStack[] getRoundTrack() {
        return roundTrack;
    }

    /**
     * Getter for the round track.
     *
     * @param index index of the cell of the round track.
     * @return a dice of the round track.
     */
    public DiceStack getRoundTrack(int index) {
        return roundTrack[index];
    }

    /**
     * Getter for players.
     *
     * @return array of all the Player.
     */
    public Player[] getPlayer() {
        return player;
    }

    /**
     * Getter for player.
     *
     * @param index of the player, domain [0, n°player-1].
     * @return the player relative to the index, null if the index is wrong.
     */
    public Player getPlayer(int index) {
        if (index < 0 || index >= player.length) return null;
        return player[index];
    }

    /**
     * Getter for tool card.
     *
     * @return an array of tool card.
     */
    public ToolCard[] getToolCard() {
        return toolCard;
    }

    /**
     * Getter for tool card.
     *
     * @param index of the Tool card, domain [0,2].
     * @return the ToolCard relative to the index, null if the index is wrong.
     */
    public ToolCard getToolCard(int index) {
        if (index < 0 || index > toolCard.length) return null;
        return toolCard[index];
    }

    /**
     * Getter for the id of a tool card.
     *
     * @param index of the Tool card domain [0,2].
     * @return the ID of the ToolCard in position of the param index, -1 if index is wrong.
     */
    public int getIdToolCard(int index) {
        if (index < 0 || index >= toolCard.length) return -1;
        return toolCard[index].getId();
    }

    /**
     * Getter for objective public card.
     *
     * @return an array of Objective Public Cards.
     */
    public ObjectivePublicCard[] getObjectivePublicCard() {
        return objectivePublicCard;
    }

    /**
     * Getter for dice pool.
     *
     * @return a DiceStack relative to the DicePool.
     */
    public DiceStack getDicePool() {
        return dicePool;
    }

    /**
     * Getter of the ID of the current player.
     *
     * @return the index relative to the current player [0, n°player-1].
     */
    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    /**
     * Getter for the current round.
     *
     * @return the number of the current round.
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Getter of the game over.
     *
     * @return true if the game over, false otherwise.
     */
    public boolean isStopGame() {
        return stopGame;
    }

    //------------------------------------------------------------------------------------------------------------------
    // OBSERVER / OBSERVABLE
    //------------------------------------------------------------------------------------------------------------------

    //************************************class's method**********************************************
    //************************************class's method**********************************************
    //************************************class's method**********************************************

    public void setStopGame(boolean stopGame) {
        this.stopGame = stopGame;
    }

    /**
     * Method used to set up the first round.
     *
     * @throws ValueDiceWrongException if the value of the dice is wrong for the game restriction.
     */
    private void setUpFirstRound() throws ValueDiceWrongException {
        //create the First Round
        stopGame = false;
        dicePool = new DiceStack();
        for (int i = 0; i < (2 * player.length + 1); i++) {
            dicePool.add(factoryDiceForThisGame.createDice());
        }
        updaterView.updateDicePool();
    }

    /**
     * Method used to have a free hand player.
     *
     * @param indexPlayer ID of the free hand player.
     */
    private void freeHandPlayer(int indexPlayer) {
        while (!player[indexPlayer].getHandDice().isEmpty()) {
            dicePool.addLast(player[indexPlayer].getHandDice().remove(0));
        }
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateDicePool();
        updaterView.updateInfoCurrentTurn();
    }

    /**
     * Method used to manage the first turn of the player.
     *
     * @param indexPlayerEnded ID of the player that ended the turn.
     */
    private void firstTurn(int indexPlayerEnded) {
        if (player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(false);
        else player[indexPlayerEnded].endTurn(true);
        freeHandPlayer(indexPlayerEnded);
        currentTurn++;
    }

    /**
     * Method used to manage the second turn of the player.
     *
     * @param indexPlayerEnded ID of the player that ended the turn.
     */
    private void secondTurn(int indexPlayerEnded) {
        if (!player[indexPlayerEnded].isFirstTurn()) player[indexPlayerEnded].endTurn(true);
        else player[indexPlayerEnded].endTurn(true);
        freeHandPlayer(indexPlayerEnded);
        currentTurn++;
    }

    /**
     * Method used t change the current player to the next and end the game.
     * It check the state of the player for the first/second turn and move the dice in hand to the DicePool.
     *
     * @param indexPlayerEnded that request the move.
     * @throws GameIsBlockedException  if the game can't be modified.
     * @throws CurrentPlayerException  if the requester isn't the current player.
     * @throws GameIsOverException     if the game is over.
     * @throws FatalGameErrorException if the game is corrupted.
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

    /**
     * Method used to calculate point of the game.
     *
     * @param indexPlayer ID of the player.
     * @return NodePodium of the player.
     */
    public NodePodium calculatePoint(int indexPlayer) {
        NodePodium newPlayerNode = new NodePodium(player[indexPlayer], objectivePublicCard);
        newPlayerNode.calculatePoint();
        return newPlayerNode;
    }

    /**
     * Method used to calculate all the point of the game.
     */
    public void calculateAllPoint() {
        TreePodium podium = new TreePodium(player.length, roundTrack.length);
        for (Player aPlayer : player) {
            NodePodium newPlayerNode = new NodePodium(aPlayer, objectivePublicCard);
            podium.insertNodePlayer(newPlayerNode);
        }
        updaterView.updatePlayerTokenAndPoints();
        updaterView.updateStatPodium(podium.getSortedPlayer(), podium.getRoot().getDescription());
    }

    //------------------------------------------------------------------------------------------------------------------
    // PLAYER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to set a window pattern.
     *
     * @param indexPlayer      ID of the player that set the window pattern.
     * @param indexOfTheWindow index of the winow pattern.
     * @throws WindowPatternAlreadyTakenException if the window pattern is already taken.
     * @throws WindowSettingCompleteException     if the window pattern are over.
     * @throws ValueDiceWrongException            if the value of the dice is wrong for the game restriction.
     */
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

    /**
     * Method used to add new dice to hand from dice pool
     *
     * @param indexPlayer   index of the player that request the move.
     * @param indexDicePool index of the dice chosen.
     * @throws NoDiceException there is no dice in the selected position.
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

    /**
     * Method used to insert a normal dice in the window pattern card.
     *
     * @param indexPlayer  ID of the player.
     * @param row          row of the window pattern card.
     * @param column       column of the window pattern card.
     * @param firstTurnDie true if the turn is over, false otherwise.
     * @throws GameException exception derivate from game restriction.
     */
    public void insertDice(int indexPlayer, int row, int column, boolean firstTurnDie) throws GameException {
        checkState(indexPlayer);
        if (player[indexPlayer].isHasPlaceANewDice()) throw new AlreadyPlaceANewDiceException();
        player[indexPlayer].insertDice(row, column, true, true, true, firstTurnDie);
        player[indexPlayer].setHasPlaceANewDice(true);
        updaterView.updatePlayerHand(indexPlayer);
        updaterView.updateSingleCell(indexPlayer, row, column);
    }

    /**
     * Method used to use a tool card.
     *
     * @param indexPlayer       ID of the player.
     * @param indexOfToolInGame index of the tool card in the game.
     * @throws GameException exception derivate from game restriction.
     */
    public void useToolCard(int indexPlayer, int indexOfToolInGame) throws GameException {
        checkState(indexPlayer);
        if (player[indexPlayer].isHasUsedToolCard()) throw new AlreadyUseToolCardException();
        toolCard[indexOfToolInGame].checkUsabilityToolCard(currentRound, player[indexPlayer]);
        player[indexPlayer].useToolCard(toolCard[indexOfToolInGame].getFavorToken());
        toolCard[indexOfToolInGame].incrementUsage();
        updaterView.updatePlayerTokenAndPoints(indexPlayer);
        updaterView.updateToolCardCost(indexOfToolInGame);
    }

    //------------------------------------------------------------------------------------------------------------------
    // TOOL'S METHOD
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method to select the restriction to remove the next dice form the window pattern.
     *
     * @param indexPlayer ID of the player.
     * @param round       round of the game.
     * @param index       index of the dice.
     * @throws GameException exception derivate from game restriction.
     */
    public void imposeColorRestriction(int indexPlayer, int round, int index) throws GameException {
        checkState(indexPlayer);
        if (round >= currentRound || round < 0) throw new RoundTrackIndexException();
        if (index < 0 || index >= roundTrack[round].size()) throw new NoDiceException();
        colorRestriction = roundTrack[round].getDice(index).getColor();
    }

    /**
     * Method used to remove a dice from the window pattern.
     *
     * @param indexPlayer      ID of the player.
     * @param row              row of the window pattern.
     * @param column           column of the window pattern.
     * @param checkRestriction if the color restriction need to be verified.
     * @throws GameException exception derivate from game restriction.
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
     * Method used to take the active dice in hand and change it with a new one.
     *
     * @param indexPlayer who send the request of the move.
     * @return true if is gone all ok, false otherwise.
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
     * Method used to swap the dice in hand and the round track.
     *
     * @param indexPlayer who send the request of the move.
     * @param round       round of the game.
     * @param indexStack  index of the dice stack of the round track.
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
     * Method use to roll the dice pool.
     *
     * @param indexPlayer who send the request of the move.
     */
    public void rollDicePool(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        if (currentTurn < player.length) throw new GameException("Non effettuare questa mossa adesso");
        dicePool.reRollAllDiceInStack();
        updaterView.updateDicePool();
    }

    //------------------------------------------------------------------------------------------------------------------
    // TOOL'S METHOD
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Method used to insert the dice on the window pattern.
     *
     * @param indexPlayer         ID of the player.
     * @param row                 index of the window's line
     * @param column              index of the window's column
     * @param adjacentRestriction true if need to be near a dice, false otherwise
     * @param colorRestriction    true if need to check this restriction
     * @param valueRestriction    true if need to check this restriction
     * @param singleNewDice       new dice.
     * @throws GameException exception derivate from game restriction.
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
     * Method used to roll a dice in hand.
     *
     * @param indexPlayer ID of the player.
     * @throws GameException exception derivate from game restriction.
     */
    public void rollDiceInHand(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].rollDiceInHand();
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * Mehod used to have the opposite face of the dice.
     *
     * @param indexPlayer ID of the player.
     * @throws GameException exception derivate from game restriction.
     */
    public void oppositeFaceDice(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].oppositeFaceDice();
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * Method used to end turn (special).
     *
     * @param indexPlayer who send the request of the move.
     * @throws GameException exception derivate from game restriction.
     */
    public void endSpecialFirstTurn(int indexPlayer) throws GameException {
        checkState(indexPlayer);
        player[indexCurrentPlayer].endSpecialFirstTurn();
        freeHandPlayer(indexPlayer);
    }

    /**
     * Method used to set value of the dice in hand.
     *
     * @param indexPlayer ID of the player.
     * @param value       value of the dice.
     * @throws GameException exception derivate from game restriction.
     */
    public void setValueDiceHand(int indexPlayer, int value) throws GameException {
        checkState(indexPlayer);
        player[indexPlayer].setValueDiceHand(value);
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * Method used to increase or decrease the value of the dice.
     *
     * @param indexPlayer integer that indicate which player send the request of
     * @param increase    true of increase the value false for decrease
     * @throws GameIsBlockedException  if the game can't be access
     * @throws CurrentPlayerException  if it isn't the player's turn
     * @throws ValueDiceWrongException if the request doesn't respect the dice domain
     * @throws NoDiceInHandException   if the player have no dice in hand
     * @throws NoDiceException         if there are no dice.
     */
    public void increaseOrDecrease(int indexPlayer, boolean increase) throws GameIsBlockedException,
            CurrentPlayerException, ValueDiceWrongException, NoDiceInHandException, NoDiceException {
        checkState(indexPlayer);
        player[indexPlayer].increaseOrDecrease(increase);
        updaterView.updatePlayerHand(indexPlayer);
    }

    /**
     * Method used to check the state of the game board and if it's or not the player turn.
     *
     * @param indexPlayer integer that indicate which player send the request of.
     * @throws GameIsBlockedException if the game can't be access.
     * @throws CurrentPlayerException if it isn't the player's turn.
     */
    private void checkState(int indexPlayer) throws GameIsBlockedException, CurrentPlayerException {
        if (stopGame) throw new GameIsBlockedException();
        if (indexPlayer != indexCurrentPlayer) throw new CurrentPlayerException();
    }
}
