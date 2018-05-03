package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.dice.DiceColor;

public class PurpleObjectivePrivateCard extends ObjectivePrivateCard {
    public PurpleObjectivePrivateCard() {
        super();
        super.setID(4);
        super.setColor(DiceColor.Purple);
        super.setName("Sfumature Viola");
        super.setDescription("Somma dei valori su tutti i dadi viola");
    }
}
