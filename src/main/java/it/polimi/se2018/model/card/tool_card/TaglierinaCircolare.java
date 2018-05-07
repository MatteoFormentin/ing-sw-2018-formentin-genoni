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
        /*
        Before:
        The Player Select One From DicePool(of the gameboard)
        ****** the Player use this card check state player need to have one dice in hand and canusedtoolcard==true
        select Dice from Roundtrank(number turn, index Diceof the dicestack)
        siwtch the 2 dice the one in hand and the one selected
        the Player Insert the dice
        end toolCard (reduce favor token of the player )
         */
    }
}
