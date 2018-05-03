package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

/**
 * Tool card Pennello per Eglomise.
 * <p>
 * Description
 * Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.
 * Devi rispettare tutte le altre restrizioni di piazzamento
 *
 * @author Matteo Formentin
 */
public class PennelloEglomise extends ToolCard {
    public PennelloEglomise() {
        super();
        super.setID(1);
        super.setName("Pennello per Eglomise");
        super.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
    }

    /**
     * Card effect..
     */
    public int effect() {
        return 0;
    }

}
