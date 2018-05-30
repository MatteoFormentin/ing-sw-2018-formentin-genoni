package it.polimi.se2018.list_event.event_controller;

/**
 * Extends EventController, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class InsertDice extends EventController {
    //from EventController private String nicknamePlayer;
    //from EventController private Model model;
    int row;
    int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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
