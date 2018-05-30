package it.polimi.se2018.list_event.event_controller;

/**
 * Extends EventController, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class EndTurn extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    @Override
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
