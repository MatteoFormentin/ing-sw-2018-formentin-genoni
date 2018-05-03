package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

/**
 * Tool card Tenaglia a Rotelle.
 * <p>
 * Description
 * Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado
 * Devi rispettare tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class RigaSughero extends ToolCard {
    public RigaSughero() {
        super();
        super.setId(8);
        super.setName("Tenaglia a Rotelle");
        super.setDescription("Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado\n" +
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
