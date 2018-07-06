package it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventClient, updates the initial length of the RoundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateNamePlayersDuringSetUp extends EventClientFromModel {

    private String[] playerNames;

    public UpdateNamePlayersDuringSetUp(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public String getPlayerNames(int index) {
        return playerNames[index];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}