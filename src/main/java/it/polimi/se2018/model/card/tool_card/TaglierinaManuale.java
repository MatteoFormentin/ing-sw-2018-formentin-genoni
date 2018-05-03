package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

/**
 * Tool card Taglierina Manuale.
 * <p>
 * Description
 * Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round
 * Devi rispettare tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class TaglierinaManuale extends ToolCard {
    public TaglierinaManuale() {
        super();
        super.setId(11);
        super.setName("Taglierina Manuale");
        super.setDescription("Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round\n" +
                "Devi rispettare tutte le restrizioni di piazzamento");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public int effect(DiceStack diceStack) {
        return 0;
    }

}
