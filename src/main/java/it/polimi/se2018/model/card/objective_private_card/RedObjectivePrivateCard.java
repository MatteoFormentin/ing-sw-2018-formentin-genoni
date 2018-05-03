package it.polimi.se2018.model.card.objective_private_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceColor;

/**
 * Pivate objective card Sfumature Rosse.
 * <p>
 * Description
 * Somma dei valori su tutti i dadi rossi
 *
 * @author Matteo Formentin
 */
public class RedObjectivePrivateCard extends ObjectivePrivateCard {
    public RedObjectivePrivateCard() {
        super();
        super.setID(4);
        super.setDiceColor(DiceColor.Red);
        super.setName("Sfumature Rosse");
        super.setDescription("Somma dei valori su tutti i dadi rossi");
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
