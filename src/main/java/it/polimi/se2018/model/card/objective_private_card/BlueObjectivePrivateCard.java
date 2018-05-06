package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.windowPatternCard.Cell;
import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.dice_factory.Dice;

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
        super.setId(3);
        super.setDiceColor(DiceColor.Blue);
        super.setName("Sfumature Blu");
        super.setDescription("Somma dei valori su tutti i dadi blu");
    }
}
