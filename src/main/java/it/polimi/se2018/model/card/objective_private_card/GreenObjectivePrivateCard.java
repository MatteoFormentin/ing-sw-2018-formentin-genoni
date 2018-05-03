package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public class GreenObjectivePrivateCard extends ObjectivePrivateCard {
    public GreenObjectivePrivateCard() {
        super();
        super.setID(2);
        super.setColor(DiceColor.Green);
        super.setName("Sfumature Verdi");
        super.setDescription("Somma dei valori su tutti i dadi verdi");
    }
}
