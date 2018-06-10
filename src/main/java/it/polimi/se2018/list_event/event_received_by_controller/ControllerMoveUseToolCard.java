package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, the controller receives the command for the tool card move
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerMoveUseToolCard extends EventController {

    @Override
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
