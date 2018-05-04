package it.polimi.se2018.model.card.tool_card;


import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Martelletto.
 * <p>
 * Description
 * Tira nuovamente tutti i dadi della Riserva
 * Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 */
public class Martelletto extends ToolCard {
    public Martelletto() {
        super();
        super.setId(6);
        super.setName("Martelletto");
        super.setDescription("Tira nuovamente tutti i dadi della Riserva\n" +
                "Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack) {

    }

}
