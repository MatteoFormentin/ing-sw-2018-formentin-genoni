package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Class
 * Extends EventController, describe the event "re-login" produced by the view.
 *
 * @author DavideMammarella
 */
public class JoinGame extends EventViewFromController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    String playerNickname;

    public String getPlayerName() {
        return playerNickname;
    }

    public void setPlayerName(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
