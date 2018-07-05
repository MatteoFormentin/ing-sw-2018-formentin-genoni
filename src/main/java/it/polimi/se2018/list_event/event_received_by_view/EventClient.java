package it.polimi.se2018.list_event.event_received_by_view;

import java.io.Serializable;

/**
 * Abstract void class for the event read by the view in a game, implements Serializable.
 * EventViews are produced by the controller and the model.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public abstract class EventClient implements Serializable {
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

    public abstract void acceptGeneric(ViewVisitor visitor);

}
