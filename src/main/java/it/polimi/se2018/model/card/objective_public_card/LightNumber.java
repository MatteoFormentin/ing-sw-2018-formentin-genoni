package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class LightNumber extends ObjectivePublicCard {
    public LightNumber() {
        super();
        super.setID(4);
        super.setName("Sfumature Chiare");
        super.setDescription("Set di 1 & 2 ovunque");
        super.setPoint(2);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
