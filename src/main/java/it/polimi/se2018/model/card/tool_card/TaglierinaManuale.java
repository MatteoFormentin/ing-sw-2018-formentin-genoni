package it.polimi.se2018.model.card.tool_card;


import it.polimi.se2018.model.dice.DiceStack;

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
    public void effect(DiceStack diceStack) {
        /*
        ****** the Player use this card check state player need to have one dice in hand and canusedtoolcard==true
        get DiceColor from one dice Of Roundtrank(number turn, index Diceof the dicestack)
        move 2 dice from windowPattern to handPlayer
        insert 2 Dice
        end toolCard (reduce favor token of the player )
         */
    }
}
