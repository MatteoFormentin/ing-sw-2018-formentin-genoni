package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

public class UpdateSingleCell extends EventView {
    private int indexPlayer;//in other word the index of the window
    private int line;
    private int column;
    private Cell cell;

    public UpdateSingleCell(int indexPlayer, int line, int column, Cell cell) {
        this.indexPlayer = indexPlayer;
        this.line = line;
        this.column = column;
        this.cell = cell;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

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

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}