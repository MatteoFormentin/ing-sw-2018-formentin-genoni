package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventView, tells the view the name of the players and their number.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class StartGame extends EventViewFromController {

    private String[] playersName;

    public String[] getPlayersName() {
        return playersName;
    }
    public String getPlayersName(int i) {
        return playersName[i];
    }
    public void setPlayersName(String[] playersName) {
        this.playersName = playersName;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
