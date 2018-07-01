package it.polimi.se2018.list_event.event_received_by_controller;


import java.io.Serializable;

/**
 * Abstract class for the EventController in a game, implements Serializable.
 * Every EventController is produced by a player.
 * (and belong to a specific game board if multiple game board)
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public abstract class EventController implements Serializable {
    private int playerId;
    private int idGame;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public abstract void accept(ControllerVisitor visitor);
}
