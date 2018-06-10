package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, tells the view to Start the turn.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class StartPlayerTurn extends EventView {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
