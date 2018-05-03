package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DifferentNumberColumn extends ObjectivePublicCard {
    public DifferentNumberColumn() {
        super();
        super.setID(3);
        super.setName("Sfumature diverse - Colonna");
        super.setDescription("Colonne senza sfumature ripetute");
        super.setPoint(4);
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
