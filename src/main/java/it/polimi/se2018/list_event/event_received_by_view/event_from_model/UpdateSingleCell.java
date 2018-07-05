package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Extends EventClient, updates the single dice of the cell
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSingleCell extends EventClientFromModel {
    private int indexPlayer;//in other word the index of the window
    private int line;
    private int column;
    private Dice dice;
    private int valueRestriction;
    private DiceColor colorRestriction;

    public UpdateSingleCell(int indexPlayer, int line, int column, Dice dice,int valueRestriction,DiceColor colorRestriction) {
        this.indexPlayer = indexPlayer;
        this.line = line;
        this.column = column;
        this.dice = dice;
        this.valueRestriction=valueRestriction;
        this.colorRestriction=colorRestriction;
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

    public int getValueRestriction() {
        return valueRestriction;
    }

    public DiceColor getColorRestriction() {
        return colorRestriction;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}