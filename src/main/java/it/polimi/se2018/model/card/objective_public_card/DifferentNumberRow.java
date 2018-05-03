package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Sfumature diverse - Riga.
 * <p>
 * Description
 * Righe senza sfumature ripetute
 *
 * @author Matteo Formentin
 */
public class DifferentNumberRow extends ObjectivePublicCard {
    public DifferentNumberRow() {
        super();
        super.setID(3);
        super.setName("Sfumature diverse - Riga");
        super.setDescription("Righe senza sfumature ripetute");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
