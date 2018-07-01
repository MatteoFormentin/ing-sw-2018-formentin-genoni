package it.polimi.se2018.list_event.event_received_by_view.event_from_server;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

public class PlayerDisconnected extends EventView {
    private int idPlayerDisconnected;

    @Override
    public void acceptGeneric(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
