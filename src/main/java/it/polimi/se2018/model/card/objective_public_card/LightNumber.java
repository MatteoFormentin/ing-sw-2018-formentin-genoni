package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;

/**
 * Public objective card Sfumature Chiare.
 * <p>
 * Description
 * Set di 1 & 2 ovunque
 *
 * @author Matteo Formentin
 */
public class LightNumber extends ObjectivePublicCard {
    public LightNumber() {
        super();
        super.setId(4);
        super.setName("Sfumature Chiare");
        super.setDescription("Set di 1 & 2 ovunque");
        super.setPoint(2);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        Cell[][] matrix = windowPatternCard.getMatrix();
        int points;
        int one = 0;
        int two = 0;
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
            }
        }
        points = super.getPoint() * Math.min(one, two);
        return points;
    }
}
