package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, describe the event "select a cell of window" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectCellOfWindowController extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int line;
    int column;
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
