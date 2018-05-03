package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

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
        super.setID(5);
        super.setName("Taglierina Circolare");
        super.setDescription("Dopo aver scelto un dado, tira nuovamente quel dado\n" +
                "Se non puoi piazzarlo, riponilo nella Riserva");
    }

    /**
     * Card effect..
     *
     * @param diceStack DiceStack (Riserva).
     */
    public int effect(DiceStack diceStack) {
        return 0;
    }

}
