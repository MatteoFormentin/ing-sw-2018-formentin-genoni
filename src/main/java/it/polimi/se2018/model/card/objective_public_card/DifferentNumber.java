package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DifferentNumber extends ObjectivePublicCard {
    public DifferentNumber() {
        super();
        super.setID(7);
        super.setName("Sfumature Diverse");
        super.setDescription("Set di dadi di ogni valore ovunque");
        super.setPoint(5);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
