package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Abstract void class for the event read by the view in a game, implements Serializable.
 * EventViews are produced by the controller.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public abstract class EventViewFromController extends EventView {

    public abstract void accept(ViewVisitor visitor);
}
