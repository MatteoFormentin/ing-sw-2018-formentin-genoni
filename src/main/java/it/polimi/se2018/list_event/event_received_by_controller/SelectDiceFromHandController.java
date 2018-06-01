package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, describe the event "select dice from hand" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromHandController extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}