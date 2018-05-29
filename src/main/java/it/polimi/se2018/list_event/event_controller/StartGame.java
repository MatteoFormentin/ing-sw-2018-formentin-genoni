package it.polimi.se2018.list_event.event_controller;

/**
 * Extends EventView, describe the event "login" produced by the view
 *
 * @author Luca Genoni
 */
public class StartGame extends EventController {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;

    public void accept(VisitorEventFromController visitor) {
        visitor.visit(this);
    }

}
