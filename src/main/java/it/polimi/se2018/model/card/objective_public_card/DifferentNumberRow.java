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
        super.setId(2);
        super.setName("Sfumature diverse - Riga");
        super.setDescription("Righe senza sfumature ripetute");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points = 0;
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int nul = 0;
        Dice currentCellDice;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                currentCellDice = matrix[i][j].getDice();
                if (currentCellDice == null) {
                    nul++;
                    continue;
                }
                if (currentCellDice.getValue() == 1) {
                    one++;
                }
                if (currentCellDice.getValue() == 2) {
                    two++;
                }
                if (currentCellDice.getValue() == 3) {
                    three++;
                }
                if (currentCellDice.getValue() == 4) {
                    four++;
                }
                if (currentCellDice.getValue() == 5) {
                    five++;
                }
                if (currentCellDice.getValue() == 6) {
                    six++;
                }
            }
            if (one <= 1 && two <= 1 && three <= 1 && four <= 1 && five <= 1 && six <= 1 && nul==0) {
                points += this.getPoint();
            }
            one = 0;
            two = 0;
            three = 0;
            four = 0;
            five = 0;
            six = 0;
        }

        return points;
    }
}
