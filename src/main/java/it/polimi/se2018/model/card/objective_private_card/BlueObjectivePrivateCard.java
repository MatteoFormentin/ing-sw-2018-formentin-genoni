package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Pivate objective card Sfumature Blu.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi blu
 *
 * @author Matteo Formentin
 */
public class BlueObjectivePrivateCard extends ObjectivePrivateCard {
    public BlueObjectivePrivateCard() {
        super();
        super.setID(3);
        super.setDiceColor(DiceColor.Blue);
        super.setName("Sfumature Blu");
        super.setDescription("Somma dei valori su tutti i dadi blu");
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
