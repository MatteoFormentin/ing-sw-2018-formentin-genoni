package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Pivate objective card Sfumature Verdi.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi verdi
 *
 * @author Matteo Formentin
 */
public class GreenObjectivePrivateCard extends ObjectivePrivateCard implements Serializable {
    public GreenObjectivePrivateCard() {
        super();
        super.setId(2);
        super.setDiceColor(DiceColor.GREEN);
        super.setName("Sfumature Verdi");
        super.setDescription("Somma dei valori su tutti i dadi verdi");
    }
}
