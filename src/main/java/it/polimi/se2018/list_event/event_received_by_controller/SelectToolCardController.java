package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, describe the event "select dice from hand" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectToolCardController extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int indexToolCard;

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
