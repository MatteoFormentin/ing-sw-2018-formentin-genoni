package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.dice.DiceStack;

public class UpdateSingleTurnRoundTrack extends EventView  {
    private int indexRound;
    private DiceStack roundDice;

    public UpdateSingleTurnRoundTrack(int indexRound, DiceStack roundDice) {
        this.indexRound = indexRound;
        this.roundDice = roundDice;
    }

    public int getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(int indexRound) {
        this.indexRound = indexRound;
    }

    public DiceStack getRoundDice() {
        return roundDice;
    }

    public void setRoundDice(DiceStack roundDice) {
        this.roundDice = roundDice;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}