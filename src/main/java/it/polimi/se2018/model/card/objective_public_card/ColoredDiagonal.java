package it.polimi.se2018.model.card.objective_public_card;

import it.polimi.se2018.model.card.windowPatternCard.WindowPatternCard;

/**
 * Public objective card Diagonali Colorate.
 * <p>
 * Description
 * Numero  di  dadi  dello  stesso  colore  diagonalmente  adiacenti
 *
 * @author Matteo Formentin
 */
public class ColoredDiagonal extends ObjectivePublicCard {
    public ColoredDiagonal() {
        super();
        super.setID(8);
        super.setName("Diagonali Colorate");
        super.setDescription("Numero  di  dadi  dello  stesso  colore  diagonalmente  adiacenti");
        super.setPoint(0); //Punti in base al numero di dadi
    }

    @Override
    public int calculatePoint(WindowPatternCard windowPatternCard) {
        return 0;
    }

}
