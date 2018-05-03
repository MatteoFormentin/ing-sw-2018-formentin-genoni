package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Pivate objective card Sfumature Viola.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi viola
 *
 * @author Matteo Formentin
 */
public class PurpleObjectivePrivateCard extends ObjectivePrivateCard {
    public PurpleObjectivePrivateCard() {
        super();
        super.setId(4);
        super.setDiceColor(DiceColor.Purple);
        super.setName("Sfumature Viola");
        super.setDescription("Somma dei valori su tutti i dadi viola");
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
