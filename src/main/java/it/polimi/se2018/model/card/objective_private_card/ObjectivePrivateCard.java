package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public abstract class ObjectivePrivateCard {
    private int ID;
    private String Name;
    private String Description;
    private DiceColor Color;


    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public DiceColor getColor() {
        return Color;
    }

    public void setColor(DiceColor color) {
        Color = color;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}