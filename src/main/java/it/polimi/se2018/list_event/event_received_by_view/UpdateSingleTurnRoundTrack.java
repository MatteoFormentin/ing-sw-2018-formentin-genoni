package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.dice.DiceStack;

public class UpdateSingleTurnRoundTrack {
    private int indexRound;
    private DiceStack dicePool;

    public int getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(int indexRound) {
        this.indexRound = indexRound;
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