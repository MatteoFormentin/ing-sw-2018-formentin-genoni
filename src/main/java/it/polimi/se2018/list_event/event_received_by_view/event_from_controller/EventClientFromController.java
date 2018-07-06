package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Abstract void class for the event read by the view in a game, implements Serializable.
 * EventViews are produced by the controller.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class EventClientFromController extends EventClient {
    @Override
    public void acceptGeneric(ViewVisitor viewVisitor) {
        viewVisitor.visit(this);
    }

    //ha bisogno dell'overriding
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        throw new UnsupportedOperationException();
    }

}
