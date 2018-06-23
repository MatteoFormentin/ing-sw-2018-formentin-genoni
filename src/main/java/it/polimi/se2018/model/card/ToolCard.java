package it.polimi.se2018.model.card;

import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.model.GameBoard;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Base class for tool card.
 * Card id starts form 0 instead of 1.
 *
 * @author Matteo Formentin
 */
public class ToolCard implements Serializable {
    private int id;
    private String name;
    private String description;
    private int favorToken=1;
    private LinkedList<EffectGame> listEffect;

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

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    public LinkedList getListEffect() {
        return listEffect;
    }

    public void setListEffect(LinkedList<EffectGame> listEffect) {
        this.listEffect = listEffect;
    }

    //************************************************************method for a general tool Card*************************
    public void incrementFavorToken() {
        favorToken = 2;
    }

}
