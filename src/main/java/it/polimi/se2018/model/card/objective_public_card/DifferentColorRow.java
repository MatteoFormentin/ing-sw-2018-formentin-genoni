package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DifferentColorRow extends ObjectivePublicCard {
    public DifferentColorRow() {
        super();
        super.setID(0);
        super.setName("Colori diversi - Riga");
        super.setDescription("Righe senza colori ripetuti");
        super.setPoint(6);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
