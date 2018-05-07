package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

import java.util.LinkedList;

/**
 * Public objective card Diagonali Colorate.
 * <p>
 * Description
 * Numero  di  dadi  dello  stesso  colore  diagonalmente  adiacenti
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

    /**
     * Private Class for the graph relationship between the cell
     */
    private class ColorCell {
        private DiceColor color;
        private boolean hasBeenVisited;
        private LinkedList<ColorCell> diagonalColorCell;

        private ColorCell(){
            color=null;
            hasBeenVisited=false;
            diagonalColorCell=new LinkedList<>();
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
                if (!diagonalColorCell.get(diagonal).hasBeenVisited && !this.hasBeenVisited){
                    diagonalColorCell.get(diagonal).hasBeenVisited=true;
                    this.hasBeenVisited=true;
                    return 2;
                }
                diagonalColorCell.get(diagonal).hasBeenVisited=true;
                this.hasBeenVisited=true;
                return 1;
            }
            return 0;
        }
    }

    /**
     * Method <strong>setWindowOfColor</strong>
     * <em>Description</em>: Private, Set the relationship of the windowOfColor's cell
     *
     * @param windowOfColor for setting the relationship between the ColorCell
     */
    private void setWindowOfColor(ColorCell[][] windowOfColor) {
        for (int line = 0; line < windowOfColor.length; line++) {
            for (int column = 0; column < windowOfColor[0].length; column++) {
                if (line == 0) { //prevent index out of bound (-1,n) if the array has dimension [4][5]
                    if (column != 0)
                        windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                    //otherwise there isn't a refer to a colorCell Index of bound! (-1,-1)
                } else if (line == (windowOfColor.length - 1)) {  //prevent index out of bound (4,n) if the array has dimension [4][5]
                    if (column != (windowOfColor[0].length - 1))
                        windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                    //otherwise there isn't a refer to a colorCell Index of bound! (4,5) if the array has dimension [4][5]
                } else {//it is an internal element, it has all the diagonal
                    windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column - 1]);
                    windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line - 1][column + 1]);
                    windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column - 1]);
                    windowOfColor[line][column].addDiagonalColorCell(windowOfColor[line + 1][column + 1]);
                }
            }

        }
    }

    /**
     * Method <strong>calculatePoint</strong>
     * <em>Description</em>
     *
     * @param windowPatternCard WindowPatternCard.
     * @return point produced by this ObjectivePublicCard
     * @author Luca Genoni
     */
    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        //variable declaration
        int counter = 0;
        Cell[][] matrix = windowPatternCard.getMatrix();
        //set the relationship between the various ColorCell, requires only 1 setting
        if (windowOfColor == null) {
            windowOfColor = new ColorCell[matrix.length][matrix[0].length];
            setWindowOfColor(windowOfColor);
        }
        //set the color of each ColorCell and HasBeenVisited to false
        Dice currentCellDice;
        for (int line = 0; line < windowOfColor.length; line++) {
            for (int column = 0; column < windowOfColor[0].length; column++) {
                currentCellDice = matrix[line][column].getCellDice();
                windowOfColor[line][column].setColor(currentCellDice.getColor());
                windowOfColor[line][column].setHasBeenVisited(false);
            }
        }
        //check for each element of windowOfColor
        for (int line = 0; line < windowOfColor.length; line++) {
            for (int column = 0; column < windowOfColor[0].length; column++) {
                for (int diagonal = 0; diagonal < windowOfColor[line][column].numberOfDiagonal(); diagonal++)
                    counter = windowOfColor[line][column].pointDiagonal(diagonal);
            }
        }
        return counter * getPoint();
    }
    /* the other method without the inner class
    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        //variable declaration
        int counter=0, secondLastLine,secondLastColumn;
        Cell[][] matrix = windowPatternCard.getMatrix();
        secondLastLine=matrix.length;
        secondLastColumn= matrix[0].length;
        DiceColor[][] windowOfColor= new DiceColor[matrix.length][matrix[0].length];
        boolean[][] composesAnExistingDiagonal = new boolean[matrix.length][matrix.length];
        Dice currentCellDice;
        //setup of the 2 "windows"
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[0].length; column++) {
                currentCellDice=matrix[line][column].getCellDice();
                windowOfColor[line][column]=currentCellDice.getColor();
                composesAnExistingDiagonal[line][column]=false;
            }
        }
        //check for the inner element if there is a diagonal element, the border element of the matrix are checked by the inner element
        for (int line = 1; line < secondLastLine; line++) {
            for (int column = 1; column < secondLastColumn; column++) {
                if(windowOfColor[line-1][column-1]==windowOfColor[line][column]) {
                    if(composesAnExistingDiagonal[line - 1][column - 1]) {
                        composesAnExistingDiagonal[line][column] = true;
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter = counter + 2;
                    }else {
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter++;
                    }
                }
                if(windowOfColor[line - 1][column + 1]==windowOfColor[line][column]) {
                    if(composesAnExistingDiagonal[line - 1][column + 1]) {
                        composesAnExistingDiagonal[line][column] = true;
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter = counter + 2;
                    }else {
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter++;
                    }
                }
                if(windowOfColor[line + 1][column - 1]==windowOfColor[line][column]) {
                    if(composesAnExistingDiagonal[line + 1][column - 1]) {
                        composesAnExistingDiagonal[line][column] = true;
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter = counter + 2;
                    }else {
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter++;
                    }
                }
                if(windowOfColor[line + 1][column + 1]==windowOfColor[line][column]) {
                    if(composesAnExistingDiagonal[line + 1][column + 1]) {
                        composesAnExistingDiagonal[line][column] = true;
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter = counter + 2;
                    }else {
                        composesAnExistingDiagonal[line - 1][column - 1] = true;
                        counter++;
                    }
                }
            }
        }
        return counter*getPoint();
    }*/


}
