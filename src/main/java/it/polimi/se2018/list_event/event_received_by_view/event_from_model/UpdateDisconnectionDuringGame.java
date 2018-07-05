package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

/**
 * Extends EventClient, updates the player disconnected during the game
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateDisconnectionDuringGame extends EventClientFromModel {
    private int index;
    private String name;

    public UpdateDisconnectionDuringGame(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
