package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Public objective card Colori diversi - Riga.
 * <p>
 * Description
 * Righe senza colori ripetuti
 *
 * @author Matteo Formentin
 */
public class DifferentColorRow extends ObjectivePublicCard {
    public DifferentColorRow() {
        super();
        super.setId(0);
        super.setName("Colori diversi - Riga");
        super.setDescription("Righe senza colori ripetuti");
        super.setPoint(6);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        boolean flag;
        Dice currentCellDice;
        Dice previousCellDice;
        for (int i = 0; i < 4; i++) {
            previousCellDice = matrix[i][0].getDice();
            flag = true;
            for (int j = 1; j < 5; j++) {
                currentCellDice = matrix[i][j].getDice();
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
