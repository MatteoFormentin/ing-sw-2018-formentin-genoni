package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, describe the event "select dice from the round track" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromRoundTrackController extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int round;
    int index;

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
