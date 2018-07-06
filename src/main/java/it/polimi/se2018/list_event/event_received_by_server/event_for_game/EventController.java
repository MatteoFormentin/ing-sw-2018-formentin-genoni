package it.polimi.se2018.list_event.event_received_by_server.event_for_game;


import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.ServerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Abstract class for the EventController in a game, implements Serializable.
 * Every EventController is produced by a player.
 * (and belong to a specific game board if multiple game board)
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class EventController extends EventServer{
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

    @Override
    public void acceptGeneric(ServerVisitor viewVisitor) {viewVisitor.visit(this);}

    //ha bisogno dell'overriding
    public void acceptInGame(ControllerVisitor visitor){throw new UnsupportedOperationException();}

}
