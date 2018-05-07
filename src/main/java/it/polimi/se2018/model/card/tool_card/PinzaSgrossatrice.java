package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Pinza Sgrossatrice.
 * <p>
 * Description
 * Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1.
 * Non puoi cambiare un 6 in 1 o un 1 in 6
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class PinzaSgrossatrice extends ToolCard {
    public PinzaSgrossatrice() {
        super();
        super.setId(0);
        super.setName("Pinza Sgrossatrice");
        super.setDescription("Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1\n" +
                "Non puoi cambiare un 6 in 1 o un 1 in 6");
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
        ****** the Player use this card check state player need to have one dice in hand and hasusedtoolcard==false
        increase or decrease by 1 the value of the dice (6 can not become 1 and vice versa)
        the Player Insert the dice
        end toolCard(reduce favor token of the player )
         */

    }
}
