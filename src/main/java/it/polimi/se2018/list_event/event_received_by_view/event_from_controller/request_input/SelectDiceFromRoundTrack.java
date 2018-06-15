package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventView, asks the view to select a dice from the roundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectDiceFromRoundTrack extends EventViewFromController {

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
