package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class MidNumber extends ObjectivePublicCard {
    public MidNumber() {
        super();
        super.setID(5);
        super.setName("Sfumature Medie");
        super.setDescription("Set di 3 & 4 ovunque");
        super.setPoint(2);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
