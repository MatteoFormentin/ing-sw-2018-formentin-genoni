package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.Cell;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.dice_factory.Dice;

/**
 * Public objective card per Sfumature Scure.
 * <p>
 * Description
 * Set di 5 & 6 ovunque
 *
 * @author Matteo Formentin
 */
public class DarkNumber extends ObjectivePublicCard {
    public DarkNumber() {
        super();
        super.setId(6);
        super.setName("Sfumature Scure");
        super.setDescription("Set di 5 & 6 ovunque");
        super.setPoint(2);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points;
        int five = 0;
        int six = 0;
        Dice currentCellDice;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                currentCellDice = matrix[i][j].getCellDice();
                if (currentCellDice.getValue() == 5) {
                    five++;
                }
                if (currentCellDice.getValue() == 6) {
                    six++;
                }
            }
        }
        points = super.getPoint() * Math.min(five, six);
        return points;
    }
}
