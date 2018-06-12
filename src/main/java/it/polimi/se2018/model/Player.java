package it.polimi.se2018.model;


import it.polimi.se2018.exception.gameboard_exception.NoDiceException;
import it.polimi.se2018.exception.*;
import it.polimi.se2018.exception.player_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.player_exception.NoDiceInHandException;
import it.polimi.se2018.exception.player_exception.NoEnoughTokenException;
import it.polimi.se2018.exception.player_exception.PlayerException;
import it.polimi.se2018.exception.window_exception.*;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;

/**
 * Player state and data. his active dice in hand is in position 0, convention
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class Player {
    private int indexInGame;
    private int favorToken;
    private int points;
    private ObjectivePrivateCard privateObject;
    private WindowPatternCard playerWindowPattern;
    private WindowPatternCard[] the4WindowPattern;
    private DiceStack handDice;
    private boolean firstTurn;
    private boolean hasDrawNewDice;
    private boolean hasPlaceANewDice;
    private boolean hasUsedToolCard;
    //sarebbe carino uno state pattern......... ma troppo sbatti

    public Player() {
        indexInGame = 0;
        favorToken = 0;
        points = 0;
        privateObject = null;
        playerWindowPattern = null;
        handDice = null;
        firstTurn = true;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        hasUsedToolCard = false;
    }

    /**
     * Constructor for a new player
     *
     * @param indexInGame of the player if needed
     */
    Player(int indexInGame) {
        this.indexInGame = indexInGame;
        favorToken = 0;
        points = 0;
        privateObject = null;
        playerWindowPattern = null;
        handDice = new DiceStack();
        firstTurn = true;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        hasUsedToolCard = false;
    }
    /*
    public Player clone(){
        Player copyPlayer =new Player();
        copyPlayer.setIndexInGame(indexInGame);
        copyPlayer.setNickName(nickName);
        copyPlayer.setFavorToken(favorToken);
        copyPlayer.setPoints(points);
        /*
        ObjectivePrivateCard privateObject;
        WindowPatternCard playerWindowPattern;
        WindowPatternCard[] the4WindowPattern;
        DiceStack handDice;*/
/*
        copyPlayer.setFirstTurn(firstTurn);
        copyPlayer.setHasDrawNewDice(hasDrawNewDice);
        copyPlayer.setHasPlaceANewDice(hasPlaceANewDice);
        copyPlayer.setHasUsedToolCard(hasUsedToolCard);
        return copyPlayer;
    }*/
    //************************************getter**********************************************
    //************************************getter**********************************************
    //************************************getter**********************************************

    public int getIndexInGame() {
        return indexInGame;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public int getPoints() {
        return points;
    }

    public ObjectivePrivateCard getPrivateObject() {
        return privateObject;
    }

    public WindowPatternCard[] getThe4WindowPattern() {
        return the4WindowPattern;
    }

    public WindowPatternCard getPlayerWindowPattern() {
        return playerWindowPattern;
    }

    public DiceStack getHandDice() {
        return handDice;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public boolean isHasDrawNewDice() {
        return hasDrawNewDice;
    }

    public boolean isHasPlaceANewDice() {
        return hasPlaceANewDice;
    }

    public boolean isHasUsedToolCard() {
        return hasUsedToolCard;
    }

    //************************************setter**********************************************
    //************************************setter**********************************************
    //************************************setter**********************************************


    public void setIndexInGame(int indexInGame) {
        this.indexInGame = indexInGame;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    public void setPlayerWindowPattern(WindowPatternCard playerWindowPattern) {
        this.playerWindowPattern = playerWindowPattern;
    }

    public void setHandDice(DiceStack handDice) {
        this.handDice = handDice;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPrivateObject(ObjectivePrivateCard privateObject) {
        this.privateObject = privateObject;
    }

    /**
     * special setter for the windowPattern
     *
     * @param index
     */
    public void choosePlayerWindowPattern(int index) {
        playerWindowPattern = the4WindowPattern[index];
        favorToken = the4WindowPattern[index].getDifficulty();
    }

    public void setThe4WindowPattern(WindowPatternCard[] the4WindowPattern) {
        this.the4WindowPattern = the4WindowPattern;
    }
/*
    public void setHandDice(DiceStack handDice) {
        this.handDice = handDice;
    }*/

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    public void setHasDrawNewDice(boolean hasDrawNewDice) {
        this.hasDrawNewDice = hasDrawNewDice;
    }

    public void setHasPlaceANewDice(boolean hasPlaceANewDice) {
        this.hasPlaceANewDice = hasPlaceANewDice;
    }

    public void setHasUsedToolCard(boolean hasUsedToolCard) {
        this.hasUsedToolCard = hasUsedToolCard;
    }


    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************
    //************************************window's method**********************************************

    /**
     * add a die to hand, without check.
     *
     * @param dice the dice to add
     * @return true if it's all ok, false otherwise
     */
    void addDiceToHand(Dice dice) throws NoDiceException {
        if (dice == null) throw new NoDiceException();
        handDice.addFirst(dice);
    }

    /**
     * A method for insert the Dice (in position 0 in hand) in the player's window with all the window restriction activated
     *
     * @param line   of the cell of the playerWindowPattern
     * @param column of the cell of the playerWindowPattern
     */
    public void insertDice(int line, int column, boolean firstDice) throws WindowRestriction, PlayerException {
       insertDice(line,column,true,true,true,firstDice);
    }

    /**
     * A method for activated the use of the toolCard methods
     * Check the state of the player and his money.
     *
     * @param cost of the tool card
     * @return true id it's all ok, false if player can't use toolcard
     */
    public void useToolCard(int cost) throws StatePlayerException,NoEnoughTokenException{
        if (hasUsedToolCard) throw new StatePlayerException();
        if (cost > favorToken) throw new NoEnoughTokenException();
        hasUsedToolCard = true;
        favorToken -= cost;
    }

    void endTrun(boolean nextTurnIsATypeFirstTurn) {
        hasUsedToolCard = false;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        firstTurn = nextTurnIsATypeFirstTurn;
    }



    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************
    //*********************************************Tool's method*************************************************

    /**
     * Method for insert the dice
     *
     * @param line index of the window's line
     * @param column index of the window's column
     * @param adjacentR true if need to be near a dice, false otherwise
     * @param colorR true if need to check this restriction
     * @param valueR true if need to check this restriction
     * @param firstInsert true if it insert a new dice for the turn
     * @throws WindowRestriction the specific exception of the insert
     * @throws PlayerException the exception regarding the state of the player
     */
    void insertDice(int line, int column, boolean adjacentR, boolean colorR, boolean valueR,boolean firstInsert)
            throws WindowRestriction,PlayerException {
        if (handDice.isEmpty()) throw new NoDiceInHandException();
        if (firstInsert && hasPlaceANewDice) throw new AlreadyPlaceANewDiceException();
        playerWindowPattern.insertDice(line, column, handDice.get(0), adjacentR, colorR, valueR);
        handDice.remove(0);
        if (firstInsert) hasPlaceANewDice = true;
    }

    /**
     * move the dice from the indicated coordinate by hand. Available when using a tool card
     *
     * @param line index of the wind's line
     * @param column index of the window's column
     * @throws WindowRestriction if something isn't right
     */
    void removeDiceFromWindow(int line, int column) throws WindowRestriction{
        Dice dice = playerWindowPattern.removeDice(line, column);
        handDice.addFirst(dice);
    }


    /**
     * the player roll the active dice (index=0) in hand. Available when using a tool card
     *
     * @return true if it's all ok, false otherwise
     */
    boolean rollDiceInHand() {
        if (!hasUsedToolCard) return false;
        if (handDice.size() == 0) return false;
        handDice.get(0).rollDice();
        return true;
    }

    /**
     * the player roll the active dice (index=0) in hand. Available when using a tool card
     *
     * @param increase true if the player want to increase the value, false for decrease
     * @return true if it's all ok, false otherwise
     */
    boolean increaseOrDecrease(boolean increase) {
        if (!hasUsedToolCard) return false;
        if (handDice.size() == 0) return false;
        handDice.get(0).increaseOrDecrease(increase);
        return true;
    }

    /**
     * the player change the face of the dice. Available when using a tool card
     *
     * @return true if it's all ok, false otherwise
     */
    boolean oppositeFaceDice() {
        if (!hasUsedToolCard) return false;
        if (handDice.size() == 0) return false;
        handDice.get(0).oppositeValue();
        return true;
    }

    /**
     * the player can now place a new dice but the second turn will be skipped. Available when using a tool card
     *
     * @return true if it's all ok, false otherwise
     */
    boolean endSpecialFirstTurn() {
        if (!hasUsedToolCard) return false;
        if (!firstTurn) return false;
        hasUsedToolCard = true;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        this.firstTurn = true;
        return true;
    }

    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************
    //*********************************************Utils*************************************************

    /**
     * change the order of the dice in hand, move the die with the index given in position 0. Available when using a tool card
     *
     * @param index of the selected dice
     * @return true if it's all ok, false otherwise
     */
    boolean selectDiceInHand(int index) {
        if (!hasUsedToolCard) return false;
        if (index >= handDice.size() || index < 0) return false;
        handDice.moveDiceToTheTop(index);
        return true;
    }
}