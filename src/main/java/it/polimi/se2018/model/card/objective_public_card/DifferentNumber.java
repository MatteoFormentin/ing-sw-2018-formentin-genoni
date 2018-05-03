package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Sfumature Diverse.
 * <p>
 * Description
 * Set di dadi di ogni valore ovunque
 *
 * @author Matteo Formentin
 */
public class DifferentNumber extends ObjectivePublicCard {
    public DifferentNumber() {
        super();
        super.setID(7);
        super.setName("Sfumature Diverse");
        super.setDescription("Set di dadi di ogni valore ovunque");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
