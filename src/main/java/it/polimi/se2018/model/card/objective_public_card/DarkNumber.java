package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DarkNumber extends ObjectivePublicCard {
    public DarkNumber() {
        super();
        super.setID(6);
        super.setName("Sfumature Scure");
        super.setDescription("Set di 5 & 6 ovunque");
        super.setPoint(2);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
