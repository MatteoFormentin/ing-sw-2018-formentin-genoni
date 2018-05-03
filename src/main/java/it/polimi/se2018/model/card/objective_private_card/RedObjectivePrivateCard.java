package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public class RedObjectivePrivateCard extends ObjectivePrivateCard {
    public RedObjectivePrivateCard() {
        super();
        super.setID(4);
        super.setColor(DiceColor.Red);
        super.setName("Sfumature Rosse");
        super.setDescription("Somma dei valori su tutti i dadi rossi");
    }
}
