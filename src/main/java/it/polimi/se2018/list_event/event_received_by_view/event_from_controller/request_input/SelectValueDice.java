package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventClient, asks the view to select a value from 1 to 6
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectValueDice extends EventClientFromController {

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
