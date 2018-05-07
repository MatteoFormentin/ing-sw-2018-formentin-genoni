package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Public objective card Sfumature diverse - Colonna.
 * <p>
 * Description
 * Colonne senza sfumature ripetute
 *
 * @author Matteo Formentin
 */
public class DifferentNumberColumn extends ObjectivePublicCard {
    public DifferentNumberColumn() {
        super();
        super.setId(3);
        super.setName("Sfumature diverse - Colonna");
        super.setDescription("Colonne senza sfumature ripetute");
        super.setPoint(4);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        boolean flag;
        Dice currentCellDice;
        Dice previousCellDice;
        for (int j = 0; j < 5; j++) {
            previousCellDice = matrix[0][j].getDice();
            flag = true;
            for (int i = 1; i < 4; i++) {
                currentCellDice = matrix[i][j].getDice();
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
