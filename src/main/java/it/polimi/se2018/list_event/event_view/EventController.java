package it.polimi.se2018.list_event.event_view;


import java.io.Serializable;

/**
 * Abstract void class for the EventController in a game, implements Serializable.
 * Every EventController is produced by a player and belong to a specific game board
 * <p>
 * SENDED FROM VIEW TO CONTROLLER
 *
 * @author Luca Genoni
 */
public abstract class EventController implements Serializable {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public abstract void accept(ControllerVisitor visitor);
}
