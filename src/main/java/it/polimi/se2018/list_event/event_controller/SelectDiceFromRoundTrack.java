package it.polimi.se2018.list_event.event_controller;

/**
 * Extends EventController, describe the event "select dice from the round track" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromRoundTrack extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int round;
    int index;

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
