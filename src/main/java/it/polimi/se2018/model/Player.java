package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
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
    private int numberDice;
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
        numberDice=0;
        secondTurn=false;
        hasUsedDice=false;
        hasUsedToolCard=false;
    }

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

    public Dice[] getHandDice() {
        return handDice;
    }

    public void setHandDice(DiceStack handDice) {
        this.handDice = handDice;
    }

    public int getNumberDice() {
        return numberDice;
    }

    public void setNumberDice(int numberDice) {
        this.numberDice = numberDice;
    }

    public void HasPlayedATurn(){
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


    public void doTurn() {
        //
        HasPlayedATurn();
    }
}