package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, tells the view that the initial part over and the main game begins
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class InitialEnded extends EventView {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
