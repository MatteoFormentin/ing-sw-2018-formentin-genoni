package it.polimi.se2018.model;


import it.polimi.se2018.model.windowPatternCard.WindowPatternCard;

public class Player {
    private int ID;
    private string Nickname;
    private int FavorToken;
    private int Points;
    private PrivateObjectiveCard PrivateObject;
    private PlayerWindowPattern WindowPattern;
    private Dice[] HandDice;
    private int NumberDice;
    private boolean SecondTurn;
    private boolean HasUsedDice;
    public boolean HasUsedToolCard;

    public void SetNickname(int ID, string nickname) {
        return null;
    }

    public int GetID() {
        return 0;
    }

    public string GetNickname() {
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