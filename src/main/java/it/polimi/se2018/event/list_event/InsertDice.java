package it.polimi.se2018.event.list_event;

/**
 * Extends EventView, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class InsertDice extends EventView {
    //from EventView private String nicknamePlayer;
    //from EventView private Model model;
    int index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
