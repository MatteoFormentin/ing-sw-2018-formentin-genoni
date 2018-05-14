package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;

import java.util.LinkedList;

/**
 * Public objective card for colored diagonal, one point for each dice of the same color in a diagonal position
 *
 * @author Luca Genoni
 */
public class ColoredDiagonal extends ObjectivePublicCard {
    private ColorCell[][] windowOfColor;

    public ColoredDiagonal() {
        super();
        super.setId(8);
        super.setName("Diagonali Colorate");
        super.setDescription("Numero  di  dadi  dello  stesso  colore  diagonalmente  adiacenti");
        super.setPoint(1); // point for each dice with the same color diagonally adjacent
        windowOfColor = null;
    }

    private class ColorCell {
        private DiceColor color;
        private boolean hasBeenVisited;
        private LinkedList<ColorCell> diagonalColorCell;

        private ColorCell() {
            color = null;
            hasBeenVisited = false;
            diagonalColorCell = new LinkedList<>();
        }

        private void setColor(DiceColor color) {
            this.color = color;
        }

        private void setHasBeenVisited(boolean hasBeenVisited) {
            this.hasBeenVisited = hasBeenVisited;
        }

        private void addDiagonalColorCell(ColorCell colorCell) {
            this.diagonalColorCell.add(colorCell);
        }

        private int numberOfDiagonal() {
            return diagonalColorCell.size();
        }

        private int pointDiagonal(int diagonal) {
            if (color == diagonalColorCell.get(diagonal).color) {
                if (diagonalColorCell.get(diagonal).hasBeenVisited && this.hasBeenVisited) return 0;
                if (!diagonalColorCell.get(diagonal).hasBeenVisited && !this.hasBeenVisited) {
                    diagonalColorCell.get(diagonal).setHasBeenVisited(true);
                    this.setHasBeenVisited(true);
                    return 2;
                }
                diagonalColorCell.get(diagonal).setHasBeenVisited(true);
                this.setHasBeenVisited(true);
                return 1;
            }
            return 0;
        }
    }

    private void setRelationWindowOfColor(ColorCell[][] windowOfColor) {
        for (int line = 0; line < windowOfColor.length; line++) {
            for (int column = 0; column < windowOfColor[0].length; column++) {

                if (line == 0) { //prevent index out of bound (-1,n) if the array has dimension [4][5]
                    if (column == 0)
                        windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column + 1]);
                    else {
                        if (column == windowOfColor[0].length-1)
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                        else {
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column + 1]);
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                        }
                    }
                    //otherwise there isn't a refer to a colorCell Index of bound! (-1,-1)
                } else {
                    if (line == (windowOfColor.length - 1)) {  //prevent index out of bound (4,n) if the array has dimension [4][5]
                        if (column == 0)
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                        else {
                            if (column == windowOfColor[0].length-1)
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column - 1]);
                            else {
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column - 1]);
                            }
                        }
                    } else {
                        if (column == 0) {
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                            windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column + 1]);
                        } else {
                            if (column == windowOfColor[0].length-1) {
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column - 1]);
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                            } else {
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column + 1]);
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                                windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column - 1]);
                            }
                        }

                    }
                }

            }
        }
    }

    /**
     * for calculate the point accumulated by this objective
     *
     * @param windowPatternCard WindowPatternCard.
     * @return point produced by this ObjectivePublicCard
     */
    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        //variable declaration
        int counter = 0;
        Cell[][] matrix = windowPatternCard.getMatrix();
        //set the relationship between the various ColorCell, requires only 1 setting
        if (windowOfColor == null) {
            windowOfColor = new ColorCell[matrix.length][matrix[0].length];
            for (int line = 0; line < windowOfColor.length; line++) {
                for (int column = 0; column < windowOfColor[0].length; column++) {
                    windowOfColor[line][column] = new ColorCell();
                }
            }
            setRelationWindowOfColor(windowOfColor);
        }
        //set the color of each ColorCell and HasBeenVisited to false
        for (int line = 0; line < windowOfColor.length; line++) {
            for (int column = 0; column < windowOfColor[0].length; column++) {
                windowOfColor[line][column].setColor(matrix[line][column].getDice().getColor());
                windowOfColor[line][column].setHasBeenVisited(false);
            }
        }
        //check for each element of windowOfColor
        for (ColorCell[] aWindowOfColor : windowOfColor) {
            for (int column = 0; column < windowOfColor[0].length; column++) {
                for (int diagonal = 0; diagonal < aWindowOfColor[column].numberOfDiagonal(); diagonal++)
                    counter += aWindowOfColor[column].pointDiagonal(diagonal);
            }
        }
        return counter * getPoint();
    }

}
