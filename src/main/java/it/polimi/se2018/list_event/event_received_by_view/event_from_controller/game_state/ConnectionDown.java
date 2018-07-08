package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

public class ConnectionDown extends EventClientFromController {

    private String cause;
    private boolean disconnectionWasRequested;

    public ConnectionDown(String cause, boolean disconnectionWasRequested) {
        this.cause = cause;
        this.disconnectionWasRequested = disconnectionWasRequested;
    }

    public String getCause() {
        return cause;
    }

    public boolean isDisconnectionWasRequested() {
        return disconnectionWasRequested;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
