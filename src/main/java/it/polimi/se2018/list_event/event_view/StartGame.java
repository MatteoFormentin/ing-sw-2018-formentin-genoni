package it.polimi.se2018.list_event.event_view;

/**
 * Extends EventController, describe the event "login" produced by the view
 *
 * @author Luca Genoni
 */
public class StartGame extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
