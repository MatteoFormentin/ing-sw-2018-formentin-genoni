package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Tool card Diluente per Pasta Salda.
 * <p>
 * Description
 * Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto
 * Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack and DiceBag.
 *
 * @author Matteo Formentin
 */
public class DiluentePastaSalda extends ToolCard {
    public DiluentePastaSalda() {
        super();
        super.setId(10);
        super.setName("Diluente per Pasta Salda");
        super.setDescription("Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto\n" +
                "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento");
    }

    /**
     * Card effect.
     *
     * @param diceStack DiceStack (Riserva).
     */
    public void effect(DiceStack diceStack/*, DiceBag diceBag*/) {
        /*
        Before:
        The Player Select One From DicePool(of the gameboard)
        ****** the Player use this card check state player need to have one dice in hand and canusedtoolcard==true
        remove from the game the Dice in hand
        the Hand Dice add a dice from factoey
        set the value of the dice
        insert the dice
        end toolCard (reduce favor token of the player )
         */
    }

}
