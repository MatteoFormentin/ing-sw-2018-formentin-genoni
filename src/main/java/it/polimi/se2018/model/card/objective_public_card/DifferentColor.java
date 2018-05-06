package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.Dice;

/**
 * Public objective card Varietà di Colore.
 * <p>
 * Description
 * Set di dadi di ogni colore ovunque
 *
 * @author Matteo Formentin
 */
public class DifferentColor extends ObjectivePublicCard {
    public DifferentColor() {
        super();
        super.setId(9);
        super.setName("Varietà di Colore");
        super.setDescription("Set di dadi di ogni colore ovunque");
        super.setPoint(4); //Punti in base al numero di dadi
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points;
        int red = 0;
        int yellow = 0;
        int green = 0;
        int blue = 0;
        int purple = 0;
        Dice currentCellDice;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                currentCellDice = matrix[i][j].getCellDice();
                if (currentCellDice.getColor() == DiceColor.Purple) {
                    red++;
                }
                if (currentCellDice.getColor() == DiceColor.Yellow) {
                    yellow++;
                }
                if (currentCellDice.getColor() == DiceColor.Green) {
                    green++;
                }
                if (currentCellDice.getColor() == DiceColor.Blue) {
                    blue++;
                }
                if (currentCellDice.getColor() == DiceColor.Purple) {
                    purple++;
                }
            }
        }
        points = super.getPoint() * Math.min(red, Math.min(yellow, Math.min(green, Math.min(blue, purple))));
        return points;
    }
}
