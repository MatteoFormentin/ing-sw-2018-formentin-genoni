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
public abstract class EventView {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
