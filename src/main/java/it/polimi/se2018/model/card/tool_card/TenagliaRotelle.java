package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Tenaglia a Rotelle.
 * <p>
 * Description
 * Dopo il tuo primo turno scegli immediatamente un altro dado
 * Salta il tuo secondo turno in questo round
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class TenagliaRotelle extends ToolCard {
    public TenagliaRotelle() {
        super();
        super.setId(7);
        super.setName("Tenaglia a Rotelle");
        super.setDescription("Dopo il tuo primo turno scegli immediatamente un altro dado\n" +
                "Salta il tuo secondo turno in questo round");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {
       /*
        ****** the Player use this card check state player need to have canusedtoolcard==true
        selectDice from DicePool
        insertDice to WindowPattern
        end toolCard (reduce favor token of the player & second turn=false);
         */
    }
}
