package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

public class DifferentColor extends ObjectivePublicCard {
    public DifferentColor() {
        super();
        super.setID(9);
        super.setName("Varietà di  Colore");
        super.setDescription("Set di dadi di ogni colore ovunque");
        super.setPoint(4); //Punti in base al numero di dadi
    }

    @Override
    int pointCounter(WindowPatternCard windowPatternCard) {
        return 0;
    }
}
