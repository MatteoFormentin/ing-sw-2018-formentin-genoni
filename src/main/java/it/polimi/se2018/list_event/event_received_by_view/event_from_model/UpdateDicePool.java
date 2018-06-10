package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.model.dice.DiceStack;

/**
 * Extends EventView, updates the dice pool
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateDicePool extends EventViewFromModel {

    private DiceStack dicePool;

    public UpdateDicePool(DiceStack dicePool) {
        this.dicePool = dicePool;
    }

    public DiceStack getDicePool() {
        return dicePool;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}