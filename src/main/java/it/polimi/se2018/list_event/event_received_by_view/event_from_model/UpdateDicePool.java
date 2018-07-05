package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Extends EventClient, updates the dice pool
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateDicePool extends EventClientFromModel {

    private DiceStack dicePool;

    public UpdateDicePool(DiceStack dicePool) {
        this.dicePool = dicePool;
    }

    public DiceStack getDicePool() {
        return dicePool;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}