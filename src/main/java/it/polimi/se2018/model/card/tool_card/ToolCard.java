package it.polimi.se2018.model.card.tool_card;

/**
 * Base class for tool card.
 * Gli id delle carte partono da 0 e sono quindi diminuiti di uno rispetto al cartaceo.
 *
 * @author Matteo Formentin
 */
public abstract class ToolCard {
    private int id;
    private String name;
    private String description;
    private int favorToken; //Punti necessari all'uso, aumentano ad ogni utilizzo

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
     * Return card Favor Token.
     *
     * @return Point int.
     */
    public int getFavorToken() {
        return favorToken;
    }

    /**
     * Increment by one card Favor Token.
     * Call after first use.
     *
     * @param point int.
     */
    public void incrementFavorToken(int point) {
        favorToken = point;
    }
}
