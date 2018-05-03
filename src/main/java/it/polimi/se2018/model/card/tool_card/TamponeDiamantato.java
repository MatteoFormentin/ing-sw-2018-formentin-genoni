package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

/**
 * Tool card Tampone Diamantato.
 * <p>
 * Description
 * Dopo aver scelto un dado, giralo sulla faccia opposta
 * 6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class TamponeDiamantato extends ToolCard {
    public TamponeDiamantato() {
        super();
        super.setId(9);
        super.setName("Tampone Diamantato");
        super.setDescription("Dopo aver scelto un dado, giralo sulla faccia opposta \n" +
                "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {

    }
}
