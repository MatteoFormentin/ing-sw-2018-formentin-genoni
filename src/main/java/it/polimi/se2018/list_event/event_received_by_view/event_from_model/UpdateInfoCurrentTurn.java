package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Extends EventView, updates a single dice Stack of the RoundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateInfoCurrentTurn extends EventViewFromModel {
    private int currentRound;
    private int currentTurn;

    public UpdateInfoCurrentTurn(int currentRound, int currentTurn) {
        this.currentRound = currentRound;
        this.currentTurn = currentTurn;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}