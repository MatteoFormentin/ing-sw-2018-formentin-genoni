package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the selected die's index from the draft pool
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectDiceFromDraftPool extends EventController {
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void acceptInGame(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
