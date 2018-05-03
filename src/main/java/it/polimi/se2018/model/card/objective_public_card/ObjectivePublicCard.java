package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Base class for objective public card.
 *
 * @author Matteo Formentin
 */
public abstract class ObjectivePublicCard {
    private int id;
    private String name;
    private String description;
    private int point;

    /**
     * Return card id.
     *
     * @return id int.
     */
    public int getId() {
        return id;
    }

    /**
     * Set card id.
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return card name.
     *
     * @return name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Set card name.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return card description.
     *
     * @return description String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set card description.
     *
     * @param description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return card objective point.
     *
     * @return point int.
     */
    public int getPoint() {
        return point;
    }

    /**
     * Set card objective point.
     *
     * @param point int.
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Calculate card point coming from objective.
     *
     * @param windowPatternCard WindowPatternCard.
     */
    public abstract int calculatePoint(WindowPatternCard windowPatternCard);

}

