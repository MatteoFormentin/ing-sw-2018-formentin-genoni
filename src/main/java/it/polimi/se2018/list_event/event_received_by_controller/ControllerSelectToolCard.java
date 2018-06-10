package it.polimi.se2018.list_event.event_received_by_controller;

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

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
