package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Class
 * Extends EventController, describe the event "re-login" produced by the view.
 *
 * @author DavideMammarella
 */
public class OkMessage extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    String playerNickname;

    public String getPlayerName() {
        return playerNickname;
    }

    public void setPlayerName(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
