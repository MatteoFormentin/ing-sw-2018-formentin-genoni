package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the command for the normal move
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerMoveDrawAndPlaceDie extends EventController {

    @Override
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
