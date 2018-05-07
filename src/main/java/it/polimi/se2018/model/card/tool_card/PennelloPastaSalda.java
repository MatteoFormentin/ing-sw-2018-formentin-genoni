package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Pennello per Pasta Salda.
 * <p>
 * Description
 * Dopo aver scelto un dado, tira nuovamente quel dado
 * Se non puoi piazzarlo, riponilo nella Riserva
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class PennelloPastaSalda extends ToolCard {
    public PennelloPastaSalda() {
        super();
        super.setId(5);
        super.setName("Pennello per Pasta Salda");
        super.setDescription("Dopo aver scelto un dado, tira nuovamente quel dado\n" +
                "Se non puoi piazzarlo, riponilo nella Riserva");
    }

    /**
     * Card effect..
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {
        /*
        Before:
        The Player Select One From DicePool(of the gameboard)
        ****** the Player use this card check state player need to have one dice in hand and canusedtoolcard==true
        reroll the dice
        insert dice or readd to dicepool
        end toolCard (reduce favor token of the player )
        (now the player can only call end turn)
         */
    }
}
