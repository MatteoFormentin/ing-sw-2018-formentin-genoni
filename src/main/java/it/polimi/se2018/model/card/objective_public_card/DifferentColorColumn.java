package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

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
        boolean flag;
        Dice currentCellDice;
        Dice previousCellDice;
        for (int j = 0; j < 5; j++) {
            previousCellDice = matrix[0][j].getCellDice();
            flag = true;
            for (int i = 1; i < 4; i++) {
                currentCellDice = matrix[i][j].getCellDice();
                if (currentCellDice.getColor() == previousCellDice.getColor()) {
                    flag = false;
                    break;
                }
                previousCellDice = currentCellDice;
            }
            if (flag) points += this.getPoint();
        }
        return points;
    }
}
