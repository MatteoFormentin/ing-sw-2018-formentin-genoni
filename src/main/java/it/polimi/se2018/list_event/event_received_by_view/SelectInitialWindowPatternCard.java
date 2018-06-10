package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, asks the view to select an initial window Pattern
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectInitialWindowPatternCard extends EventView  {

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
