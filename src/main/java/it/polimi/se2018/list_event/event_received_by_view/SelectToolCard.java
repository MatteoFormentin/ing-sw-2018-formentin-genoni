package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, asks the view to select a Toolcard
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectToolCard extends EventView {

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
