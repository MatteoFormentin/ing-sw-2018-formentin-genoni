package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

/**
 * Pivate objective card Sfumature Gialle.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi gialli
 *
 * @author Matteo Formentin
 */
public class YellowObjectivePrivateCard extends ObjectivePrivateCard {
    public YellowObjectivePrivateCard() {
        super();
        super.setId(1);
        super.setDiceColor(DiceColor.Yellow);
        super.setName("Sfumature Gialle");
        super.setDescription("Somma dei valori su tutti i dadi gialli");
    }
}
