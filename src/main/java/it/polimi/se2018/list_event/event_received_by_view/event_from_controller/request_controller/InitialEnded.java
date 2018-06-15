package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventView, tells the view that the initial part over and the main game begins
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class InitialEnded extends EventViewFromController {

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
