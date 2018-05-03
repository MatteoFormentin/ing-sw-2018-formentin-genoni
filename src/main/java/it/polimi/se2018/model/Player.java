package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Player state and data.
 *
 * @author Matteo Formentin
 */
public class Player {
    private int id;
    private String nickname;
    private int favorToken;
    private int points;
    private ObjectivePrivateCard privateObject;
    private WindowPatternCard playerWindowPattern;
    private Dice[] handDice;
    private int numberDice;
    private boolean secondTurn;
    private boolean hasUsedDice;
    private boolean hasUsedToolCard;


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

    public void setHandDice(Dice[] handDice) {
        this.handDice = handDice;
    }

    public int getNumberDice() {
        return numberDice;
    }

    public void setNumberDice(int numberDice) {
        this.numberDice = numberDice;
    }

    public boolean isSecondTurn() {
        return secondTurn;
    }

    public void setSecondTurn(boolean secondTurn) {
        this.secondTurn = secondTurn;
    }

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

}