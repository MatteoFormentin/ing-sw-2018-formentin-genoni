package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Base class for objective public card.
 *
 * @author Matteo Formentin
 */
public abstract class ObjectivePublicCard {
    private int ID;
    private String Name;
    private String Description;
    private int Point;

    /**
     * Return card ID.
     *
     * @return ID int.
     */
    public int getID() {
        return ID;
    }

    /**
     * Set card ID.
     *
     * @param ID int
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Return card name.
     *
     * @return Name String.
     */
    public String getName() {
        return Name;
    }

    /**
     * Set card name.
     *
     * @param name String
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Return card description.
     *
     * @return Description String.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Set card description.
     *
     * @param description String.
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Return card objective point.
     *
     * @return Point int.
     */
    public int getPoint() {
        return Point;
    }

    /**
     * Set card objective point.
     *
     * @param point int.
     */
    public void setPoint(int point) {
        Point = point;
    }

    /**
     * Calculate card point coming from objective.
     *
     * @param windowPatternCard WindowPatternCard.
     */
    public abstract int calculatePoint(WindowPatternCard windowPatternCard);

}

