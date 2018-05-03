package it.polimi.se2018.model.card.windowPatternCard;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

public class WindowPatternCard {

    public WindowPatternCard(){
    }

    private int ID;
    private String Name;
    private int Level;
    private Cell[][] Matrix; //[4][5] inizializzare nel costruttore

    public String GetName() {
        return null;
    }

    public void SetName(String Name) {
    }

    public int GetLevel() {
        return 0;
    }

    public void SetLevel(int Level) {
    }

    public Cell GetCell(int Line, int Column) {
        return null;
    }

    public boolean CheckColorRestriction(int Line, int Column, DiceColor Color) {
        return false;
    }

    public boolean CheckValueRestriction(int Line, int Column, int Value) {
        return false;
    }

    public boolean CheckAdjacentRestriction(int Line, int Column) {
        return false;
    }

    public boolean InsertDice(int Line, int Column, Dice Dice) {
        return false;
    }

    public boolean InsertDice(int Line, int Column, Dice Dice, boolean ColorR, boolean AdjacentR, boolean ValueR) {
        return false;
    }

}
