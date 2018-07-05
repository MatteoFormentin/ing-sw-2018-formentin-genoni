package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the selected tool card's index that the player want to use.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectToolCard extends EventController {
    private int indexToolCard;

    public int getIndexToolCard() {
        return indexToolCard;
    }

    public void setIndexToolCard(int index) {
        this.indexToolCard = index;
    }

    @Override
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
