package it.polimi.se2018.model;


import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

public class Player {
    private int ID;
    private String Nickname;
    private int FavorToken;
    private int Points;
    private ObjectivePrivateCard PrivateObject;
    private WindowPatternCard PlayerWindowPattern;
    private Dice[] HandDice;
    private int NumberDice;
    private boolean SecondTurn;
    private boolean HasUsedDice;
    public boolean HasUsedToolCard;

    public void SetNickname(int ID, String nickname) {
    }

    public int GetID() {
        return 0;
    }

    public String GetNickname() {
        return null;
    }

    public void SetFavorToken(int FavorToken) {
    }

    public int GetFavorToken() {
        return 0;
    }

    public void SetWindowPattern(WindowPatternCard WindowPattern) {

    }

}