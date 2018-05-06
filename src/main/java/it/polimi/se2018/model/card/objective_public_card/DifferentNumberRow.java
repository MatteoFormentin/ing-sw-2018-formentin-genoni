package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Public objective card Sfumature diverse - Riga.
 * <p>
 * Description
 * Righe senza sfumature ripetute
 *
 * @author Matteo Formentin
 */
public class DifferentNumberRow extends ObjectivePublicCard {
    public DifferentNumberRow() {
        super();
        super.setId(3);
        super.setName("Sfumature diverse - Riga");
        super.setDescription("Righe senza sfumature ripetute");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        boolean flag;
        Dice currentCellDice;
        Dice previousCellDice;
        for (int i = 0; i < 4; i++) {
            previousCellDice = matrix[i][0].getCellDice();
            flag = true;
            for (int j = 1; j < 5; j++) {
                currentCellDice = matrix[i][j].getCellDice();
                if (currentCellDice.getValue() == previousCellDice.getValue()) {
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
