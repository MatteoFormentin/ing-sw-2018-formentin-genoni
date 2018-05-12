package it.polimi.se2018.event.list_event;


import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Model;

import java.io.Serializable;

/**
 * Abstract void class for the EventView in a game, implements Serializable.
 * Every EventView is produced by a player and belong to a specific game board
 *
 * @author Luca Genoni
 */
public abstract class EventView implements Serializable {
    private String nicknamPlayer;
    private Model model;

    public void setNicknamPlayer(String nicknamPlayer) {
        this.nicknamPlayer = nicknamPlayer;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public String getNicknamPlayer() {
        return nicknamPlayer;
    }

}
