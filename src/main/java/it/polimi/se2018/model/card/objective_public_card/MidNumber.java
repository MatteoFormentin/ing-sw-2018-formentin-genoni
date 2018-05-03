package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Sfumature Medie.
 * <p>
 * Description
 * Set di 3 & 4 ovunque
 *
 * @author Matteo Formentin
 */
public class MidNumber extends ObjectivePublicCard {
    public MidNumber() {
        super();
        super.setId(5);
        super.setName("Sfumature Medie");
        super.setDescription("Set di 3 & 4 ovunque");
        super.setPoint(2);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
