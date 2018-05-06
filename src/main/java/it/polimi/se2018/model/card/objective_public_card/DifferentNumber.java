package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.Cell;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.dice_factory.Dice;

/**
 * Public objective card Sfumature Diverse.
 * <p>
 * Description
 * Set di dadi di ogni valore ovunque
 *
 * @author Matteo Formentin
 */
public class DifferentNumber extends ObjectivePublicCard {
    public DifferentNumber() {
        super();
        super.setId(7);
        super.setName("Sfumature Diverse");
        super.setDescription("Set di dadi di ogni valore ovunque");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points;
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        Dice currentCellDice;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                currentCellDice = matrix[i][j].getCellDice();
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
        }
        points = super.getPoint() * Math.min(one, Math.min(two, Math.min(three, Math.min(four, Math.min(five, six)))));
        return points;
    }
}
