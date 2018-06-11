package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Public objective card Colori diversi - Colonna.
 * <p>
 * Description
 * Colonne senza colori ripetuti
 *
 * @author Matteo Formentin
 */
public class DifferentColorColumn extends ObjectivePublicCard {
    public DifferentColorColumn() {
        super();
        super.setId(1);
        super.setName("Colori diversi - Colonna");
        super.setDescription("Colonne senza colori ripetuti");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        int red = 0;
        int yellow = 0;
        int green = 0;
        int blue = 0;
        int purple = 0;
        Dice currentCellDice;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) {
                currentCellDice = matrix[i][j].getDice();
                if (currentCellDice == null) continue;

                if (currentCellDice.getColor() == DiceColor.RED) {
                    red++;
                }
                if (currentCellDice.getColor() == DiceColor.YELLOW) {
                    yellow++;
                }
                if (currentCellDice.getColor() == DiceColor.GREEN) {
                    green++;
                }
                if (currentCellDice.getColor() == DiceColor.BLUE) {
                    blue++;
                }
                if (currentCellDice.getColor() == DiceColor.PURPLE) {
                    purple++;
                }
            }

            if (red <= 1 && yellow <= 1 && green <= 1 && blue <= 1 && purple <= 1) {
                points += this.getPoint();
            }
            red = 0;
            yellow = 0;
            green = 0;
            blue = 0;
            purple = 0;
        }
        return points;
    }
}
