package it.polimi.se2018.list_event.event_received_by_controller;

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
    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
