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

    }
}
