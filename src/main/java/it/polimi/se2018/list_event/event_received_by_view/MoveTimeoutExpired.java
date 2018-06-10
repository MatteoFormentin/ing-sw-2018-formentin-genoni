package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, tells the view that the time for his turn is over
 *
 * @author Matteo Formentin
 */
public class MoveTimeoutExpired extends EventView {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
