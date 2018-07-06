package it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller;

import it.polimi.se2018.list_event.event_received_by_server.event_for_game.ControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;

/**
 * Extends EventController, the controller receives the selected window's coordinates
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectCellOfWindow extends EventController {
    private int line;
    private int column;
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public void acceptInGame(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
