package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

import java.io.Serializable;

/**
 * Pivate objective card Sfumature Viola.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi viola
 *
 * @author Matteo Formentin
 */
public class PurpleObjectivePrivateCard extends ObjectivePrivateCard implements Serializable {
    public PurpleObjectivePrivateCard() {
        super();
        super.setId(4);
        super.setDiceColor(DiceColor.PURPLE);
        super.setName("Sfumature Viola");
        super.setDescription("Somma dei valori su tutti i dadi viola");
    }
}
