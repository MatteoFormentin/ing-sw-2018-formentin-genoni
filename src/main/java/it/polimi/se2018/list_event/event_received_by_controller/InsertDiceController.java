package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class InsertDiceController extends EventController {
    //from EventController private String nicknamePlayer;
    //from EventController private Model model;
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
