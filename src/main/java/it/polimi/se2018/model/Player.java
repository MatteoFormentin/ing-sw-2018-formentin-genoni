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
    private DiceStack handDice;
    private boolean secondTurn;
    private boolean hasUsedDice;
    private boolean hasUsedToolCard;

    public Player(){
        id=0;
        nickname="Mr. Nessuno";
        favorToken=0;
        points=0;
        privateObject=null;
        playerWindowPattern=null;
        handDice=null;
        secondTurn=false;
        hasUsedDice=false;
        hasUsedToolCard=false;
    }
    public Player(String nickname,int id){
        this.id=id;
        this.nickname=nickname;
        favorToken=0;
        points=0;
        privateObject=null;
        playerWindowPattern=null;
        handDice=null;
        secondTurn=false;
        hasUsedDice=false;
        hasUsedToolCard=false;
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

    public Dice getHandDice(int index) {
        return handDice.getDice(index);
    }

    public void setHandDice(DiceStack handDice) {
        this.handDice = handDice;
    }

    public void changeSecondTurn(){
        secondTurn=!secondTurn;
    }

    public boolean isSecondTurn() {return secondTurn;}

    public boolean isHasUsedDice() {
        return hasUsedDice;
    }

    public void setHasUsedDice(boolean hasUsedDice) {
        this.hasUsedDice = hasUsedDice;
    }

    public boolean isHasUsedToolCard() {
        return hasUsedToolCard;
    }

    public void setHasUsedToolCard(boolean hasUsedToolCard) {
        this.hasUsedToolCard = hasUsedToolCard;
    }

    /**
     * return exeption can't inset dice
     * @param line of the cell of the playerWindowPattern
     * @param column of the cell of the playerWindowPattern
     * @param indexOfTheDiceInHand to insert in the WindowsPattern
     * return exeption
     */
    public void insetDice (int line, int column, int indexOfTheDiceInHand)throws Exception{
        // check if the player has insert the dice
        if(hasUsedDice) throw new Exception(); // new exception already inserted
        // the player can insert the Dice
        try{
            if(playerWindowPattern.insertDice(line,column,handDice.getDice(indexOfTheDiceInHand)))handDice.removeDiceFromStack(indexOfTheDiceInHand);
        }catch (Exception Exception){
            return;
        }
        hasUsedDice=true;
    }

    /**
     * @param line index [0,3] of the WindowsPattern
     * @param column index [0,4] of the WindowsPattern
     * @param indexOfTheDiceInHand to insert in the WindowsPattern
     * @param colorRestriction true if the Restriction need to be respected, false if it is ignored:
     *                         colorRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                         if this logic produce true can insert the dice
     * @param valueRestriction true if the Restriction need to be respected, false if it is ignored:
     *                         valueRestriction->the dice respect the restriction, == !valueRestriction || respect the restriction
     *                         if this logic produce true can insert the dice
     * @param adjacentRestriction true if the Restriction need to be respected, false if the Restriction need to be violated
     *                            adjacentRestriction==the dice respect the restriction
     *                            if this logic produce true can insert the dice
     * @throws Exception
     */
    public void insetDice(int line, int column, int indexOfTheDiceInHand, boolean adjacentRestriction,
                           boolean colorRestriction, boolean valueRestriction)throws Exception{
        // check if the player has insert the dice
        if(hasUsedDice) throw new Exception(); // new exception already inserted
        // the player can insert the Dice
        try{
            playerWindowPattern.insertDice(line,column,handDice.getDice(indexOfTheDiceInHand));
            handDice.removeDiceFromStack(indexOfTheDiceInHand);
        }catch (Exception Exception){
            return ;
        }
        hasUsedDice=true;
    }
    public void removeDiceFromWindowPattern(int line, int column){
        handDice.addDice(playerWindowPattern.getDice(line,column));
        playerWindowPattern.removeDice(line,column);
    }

    /**
     *
     * @param cost of the ToolCard
     * @return boolean true can use the ToolCard
     * !HasUsedDice && favorToken>=0 both true for return true
     *
     */
    public boolean canuseToolCard(int cost){
        return(!hasUsedDice &&(favorToken-cost>=0));

    }
    public void HasUsedToolCard(int cost){
        hasUsedDice=true;
        favorToken=favorToken-cost;
    }

}