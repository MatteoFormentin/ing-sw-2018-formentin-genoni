package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;

/**
 * Player state and data.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 * @version 1.1
 * @since 1.0
 */
public class Player {
    private int id;
    private String nickname;
    private int favorToken;
    private int points;
    private ObjectivePrivateCard privateObject;
    private WindowPatternCard playerWindowPattern;
    private WindowPatternCard[] choiceWindowPattern = new WindowPatternCard[4];
    private DiceStack handDice;
    private boolean secondTurn;
    private boolean hasDrawNewDice;
    private boolean hasPlaceANewDice;
    private boolean hasUsedToolCard;

    public Player() {
        id = 0;
        nickname = "Mr. Nessuno";
        favorToken = 0;
        points = 0;
        privateObject = null;
        playerWindowPattern = null;
        handDice = null;
        secondTurn = false;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        hasUsedToolCard = false;
    }

    /**
     * Constructor for a new player
     *
     * @param nickname
     * @param id
     */
    public Player(String nickname, int id) {
        this.id = id;
        this.nickname = nickname;
        favorToken = 0;
        points = 0;
        privateObject = null;
        playerWindowPattern = null;
        handDice = null;
        secondTurn = false;
        hasDrawNewDice = false;
        hasPlaceANewDice = false;
        hasUsedToolCard = false;
    }

    /* setter & getter*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    /**
     * comfortable method for take away the token when a tool card is activated
     *
     * @param cost of the tool card used
     */
    public void useFavorToken(int cost) {
        this.favorToken -= cost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ObjectivePrivateCard getPrivateObject() {
        return privateObject;
    }

    public void setPrivateObject(ObjectivePrivateCard privateObject) {
        this.privateObject = privateObject;
    }

    public WindowPatternCard getPlayerWindowPattern() {
        return playerWindowPattern;
    }

    public void setPlayerWindowPattern(WindowPatternCard playerWindowPattern) {
        this.playerWindowPattern = playerWindowPattern;
    }

    public WindowPatternCard getChoiceWindowPattern(int index) {
        return choiceWindowPattern[index];
    }

    public void setChoiceWindowPattern(WindowPatternCard[] choiceWindowPattern) {
        this.choiceWindowPattern = choiceWindowPattern;
    }

    /**
     * method when the player select the dice from the draftpool
     *
     * @param dice
     */
    public void moveDiceToHand(Dice dice) {
        hasDrawNewDice = true;
        handDice.addDice(dice);
    }

    public void addDiceToHandFromFactory() {
        handDice.createDice(1);
    }

    public Dice removeDiceFromHand(int index) {
        return handDice.removeDiceFromStack(index);
    }

    public DiceStack getHandDice() {
        return handDice;
    }

    public void setSecondTurn(boolean secondTurn) {
        this.secondTurn = secondTurn;
    }

    public boolean isSecondTurn() {
        return secondTurn;
    }

    public boolean isHasDrawNewDice() {
        return hasDrawNewDice;
    }

    public void setHasDrawNewDice(boolean hasDrawNewDice) {
        this.hasDrawNewDice = hasDrawNewDice;
    }

    public boolean isHasPlaceANewDice() {
        return hasPlaceANewDice;
    }

    public void setHasPlaceANewDice(boolean hasPlaceANewDice) {
        this.hasPlaceANewDice = hasPlaceANewDice;
    }

    /**
     * Check the move of the player
     *
     * @return true if he has used the Tool in this Turn, False otherwise
     */
    public boolean isHasUsedToolCard() {
        return hasUsedToolCard;
    }
    /**
     * Set true if a player use a Tool card
     *
     */
    /**
     * method for set the control of the move available
     *
     * @param hasUsedToolCard true if player use the card, false when the turn change
     */
    public void setHasUsedToolCard(boolean hasUsedToolCard) {
        this.hasUsedToolCard = hasUsedToolCard;
    }

    /**
     * method for insert the dice in hand, with the index 0(for select the dice see the DiceStack)
     *
     * @param line   of the cell of the playerWindowPattern
     * @param column of the cell of the playerWindowPattern
     */
    public void insertDice(int line, int column) throws Exception {
        // check if the player has insert the dice
        if (hasDrawNewDice) throw new Exception(); // new exception already inserted
        // the player can insert the Dice
        try {
            if (!playerWindowPattern.insertDice(line, column, handDice.getDice(0))) return; // or exception
            removeDiceFromHand(0);
            hasPlaceANewDice = true;
        } catch (Exception Exception) {
            return;
        }
        hasDrawNewDice = true;
    }

    /**
     * method for insert the dice in hand, with the index 0(for select the dice see the DiceStack)
     *
     * @param line                index [0,3] of the WindowsPattern
     * @param column              index [0,4] of the WindowsPattern
     * @param colorRestriction    true if the Restriction need to be respected, false if it is ignored:
     *                            colorRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                            if this logic produce true can insert the dice
     * @param valueRestriction    true if the Restriction need to be respected, false if it is ignored:
     *                            valueRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                            if this logic produce true can insert the dice
     * @param adjacentRestriction true if the Restriction need to be respected, false if the Restriction need to be violated
     *                            adjacentRestriction==the dice respect the restriction
     *                            if this logic produce true can insert the dice
     */
    public void insertDice(int line, int column, boolean adjacentRestriction,
                           boolean colorRestriction, boolean valueRestriction) throws Exception {
        // check if the player has insert the dice
        if (hasDrawNewDice) throw new Exception(); // new exception already inserted
        // the player can insert the Dice
        try {
            if (!playerWindowPattern.insertDice(line, column, handDice.getDice(0),adjacentRestriction,colorRestriction,valueRestriction)) return; // or exception
            removeDiceFromHand(0);
            hasPlaceANewDice = true;
        } catch (Exception Exception) {
            return;
        }
    }

    public void removeDiceFromWindowPattern(int line, int column) {
        handDice.addDice(playerWindowPattern.getDice(line, column));
        playerWindowPattern.removeDice(line, column);
    }
    public void rollDiceInHand(int index){
        handDice.getDice(index).rollDice();
    }

}