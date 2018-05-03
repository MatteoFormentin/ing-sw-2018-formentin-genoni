package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.roundTrack.DiceStack;

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

    }

}
