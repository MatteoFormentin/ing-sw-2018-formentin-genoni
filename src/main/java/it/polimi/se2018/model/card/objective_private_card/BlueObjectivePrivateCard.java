package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Pivate objective card Sfumature Blu.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi blu
 *
 * @author Matteo Formentin
 */
public class BlueObjectivePrivateCard extends ObjectivePrivateCard implements Serializable {
    public BlueObjectivePrivateCard() {
        super();
        super.setId(3);
        super.setDiceColor(DiceColor.Blue);
        super.setName("Sfumature Blu");
        super.setDescription("Somma dei valori su tutti i dadi blu");
    }
}
