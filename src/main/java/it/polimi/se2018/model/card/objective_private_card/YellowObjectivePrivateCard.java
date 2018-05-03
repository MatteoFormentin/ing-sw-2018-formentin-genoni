package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public class YellowObjectivePrivateCard extends ObjectivePrivateCard {
    public YellowObjectivePrivateCard() {
        super();
        super.setID(4);
        super.setColor(DiceColor.Yellow);
        super.setName("Sfumature Gialle");
        super.setDescription("Somma dei valori su tutti i dadi gialli");
    }
}
