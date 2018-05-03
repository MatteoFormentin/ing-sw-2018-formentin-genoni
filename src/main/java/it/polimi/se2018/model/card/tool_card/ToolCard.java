package it.polimi.se2018.model.card.tool_card;

/**
 * Base class for tool card.
 * Gli ID delle carte partono da 0 e sono quindi diminuiti di uno rispetto al cartaceo.
 *
 * @author Matteo Formentin
 */
public abstract class ToolCard {
    private int ID;
    private String Name;
    private String Description;
    private int FavorToken; //Punti necessari all'uso, aumentano ad ogni utilizzo

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
     * Return card Favor Token.
     *
     * @return Point int.
     */
    public int getFavorToken() {
        return FavorToken;
    }

    /**
     * Increment by one card Favor Token.
     * Call after first use.
     *
     * @param point int.
     */
    public void incrementFavorToken(int point) {
        FavorToken = point;
    }
}
