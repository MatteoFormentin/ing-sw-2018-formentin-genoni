package it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventViewFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventView, updates the initial length of the RoundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateNamePlayers extends EventViewFromModel {

    private String[] playerNames;

    public UpdateNamePlayers(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }
    public String getNames(int index) {
        return playerNames[index];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}