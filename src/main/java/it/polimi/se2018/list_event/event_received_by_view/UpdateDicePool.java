package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.dice.DiceStack;

public class UpdateDicePool extends EventView {

    private DiceStack dicePool;

    public UpdateDicePool(DiceStack dicePool) {
        this.dicePool = dicePool;
    }

    public DiceStack getDicePool() {
        return dicePool;
    }

    public void setDicePool(DiceStack dicePool) {
        this.dicePool = dicePool;
    }


    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}