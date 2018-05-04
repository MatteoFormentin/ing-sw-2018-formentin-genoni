package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Riga in Sughero.
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
        super.setName("Riga in Sughero");
        super.setDescription("Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado\n" +
                "Devi rispettare tutte le restrizioni di piazzamento");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {

    }
}
