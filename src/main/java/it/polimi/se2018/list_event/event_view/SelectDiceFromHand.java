package it.polimi.se2018.list_event.event_view;

/**
 * Extends EventController, describe the event "select dice from hand" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromHand extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int index;

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
