package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.model.dice.Dice;

/**
 * Extends EventView, updates the single dice of the cell
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSingleCell extends EventViewFromModel {
    private int indexPlayer;//in other word the index of the window
    private int line;
    private int column;
    private Dice dice;

    public UpdateSingleCell(int indexPlayer, int line, int column, Dice dice) {
        this.indexPlayer = indexPlayer;
        this.line = line;
        this.column = column;
        this.dice = dice;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Dice getDice() {
        return dice;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}