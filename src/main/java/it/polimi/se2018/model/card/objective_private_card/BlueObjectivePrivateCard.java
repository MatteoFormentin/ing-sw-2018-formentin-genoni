package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public class BlueObjectivePrivateCard extends ObjectivePrivateCard {
    public BlueObjectivePrivateCard() {
        super();
        super.setID(3);
        super.setColor(DiceColor.Blue);
        super.setName("Sfumature Blu");
        super.setDescription("Somma dei valori su tutti i dadi blu");
    }

}
