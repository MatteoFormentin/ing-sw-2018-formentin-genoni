package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card utensile Taglierina Circolare.
 * <p>
 * Description
 * Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class TaglierinaCircolare extends ToolCard {
    public TaglierinaCircolare() {
        super();
        super.setId(4);
        super.setName("Taglierina Circolare");
        super.setDescription("Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {

    }
}
