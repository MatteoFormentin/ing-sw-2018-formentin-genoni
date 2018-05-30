package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventController, describe the event "login" produced by the view
 *
 * @author Luca Genoni
 */
public class StartGame extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    String[] playersName;

    public String[] getPlayersName() {
        return playersName;
    }

    public void setPlayersName(String[] playersName) {
        this.playersName = playersName;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
