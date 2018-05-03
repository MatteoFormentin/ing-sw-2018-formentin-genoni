package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Sfumature Chiare.
 * <p>
 * Description
 * Set di 1 & 2 ovunque
 *
 * @author Matteo Formentin
 */
public class LightNumber extends ObjectivePublicCard {
    public LightNumber() {
        super();
        super.setId(4);
        super.setName("Sfumature Chiare");
        super.setDescription("Set di 1 & 2 ovunque");
        super.setPoint(2);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
