package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Varietà di Colore.
 * <p>
 * Description
 * Set di dadi di ogni colore ovunque
 *
 * @author Matteo Formentin
 */
public class DifferentColor extends ObjectivePublicCard {
    public DifferentColor() {
        super();
        super.setID(9);
        super.setName("Varietà di Colore");
        super.setDescription("Set di dadi di ogni colore ovunque");
        super.setPoint(4); //Punti in base al numero di dadi
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
