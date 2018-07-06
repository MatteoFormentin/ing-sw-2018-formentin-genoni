package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the selected window's coordinates
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerInfoEffect extends EventController {
    private int[] Info;


    public int[] getInfo() {
        return Info;
    }

    public void setInfo(int[] info) {
        Info = info;
    }

    @Override
    public void acceptInGame(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
