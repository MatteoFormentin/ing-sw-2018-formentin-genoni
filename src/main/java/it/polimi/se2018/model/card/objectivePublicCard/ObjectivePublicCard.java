package it.polimi.se2018.model.card.objectivePublicCard;

public abstract class ObjectivePublicCard {
    private int ID;
    private String Name;
    private String Description;
    private int Point;

    abstract int pointCounter();
}
