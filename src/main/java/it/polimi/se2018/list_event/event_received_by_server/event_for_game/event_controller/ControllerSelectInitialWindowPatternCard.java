package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the selected window's index of the initial windows
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectInitialWindowPatternCard extends EventController {
    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
