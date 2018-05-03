package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DifferentColorColumn extends ObjectivePublicCard {
    public DifferentColorColumn() {
        super();
        super.setID(1);
        super.setName("Colori diversi - Colonna");
        super.setDescription("Colonne senza colori ripetuti");
        super.setPoint(5);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
