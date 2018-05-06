package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Pivate objective card Sfumature Verdi.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi verdi
 *
 * @author Matteo Formentin
 */
public class GreenObjectivePrivateCard extends ObjectivePrivateCard {
    public GreenObjectivePrivateCard() {
        super();
        super.setId(2);
        super.setDiceColor(DiceColor.Green);
        super.setName("Sfumature Verdi");
        super.setDescription("Somma dei valori su tutti i dadi verdi");
    }
}
