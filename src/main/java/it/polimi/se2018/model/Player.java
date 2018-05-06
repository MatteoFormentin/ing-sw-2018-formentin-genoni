package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;

/**
 * Player state and data.
 *
 * @author Matteo Formentin
 * @version 1.1
 * Genoni illegally modifies:
 *                   the secondTurn attribute & method
 *                   the Default Constructor
 *                   the array handDice became a DiceStack the only Class that can handle
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
     * @param indexDiceOfHandDice the index of the dice in the Hand of the player
     * return exeption
     */
    public void insetDice (int line, int column, int indexDiceOfHandDice)throws Exception{
        // check if the player has insert the dice
        if(hasUsedDice) throw new Exception(); // new exception already inserted
        // the player can insert the Dice
        try{
            playerWindowPattern.insertDice(line,column,handDice.removeDiceFromStack(indexDiceOfHandDice));
        }catch (Exception Exception){
            return;
        }
        hasUsedDice=true;
    }
    public void addDiceToHandDice(Dice dice){
            handDice.addDice(dice);
    }

}