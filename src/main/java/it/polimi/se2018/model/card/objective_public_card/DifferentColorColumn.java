package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Colori diversi - Colonna.
 * <p>
 * Description
 * Colonne senza colori ripetuti
 *
 * @author Matteo Formentin
 */
public class DifferentColorColumn extends ObjectivePublicCard {
    public DifferentColorColumn() {
        super();
        super.setID(1);
        super.setName("Colori diversi - Colonna");
        super.setDescription("Colonne senza colori ripetuti");
        super.setPoint(5);
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
