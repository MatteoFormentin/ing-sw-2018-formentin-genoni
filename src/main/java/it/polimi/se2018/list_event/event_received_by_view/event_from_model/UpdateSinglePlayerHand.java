package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.model.dice.DiceStack;

/**
 * Extends EventView, updates the hand of the player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSinglePlayerHand extends EventViewFromModel {
    private int indexPlayer;
    private DiceStack handPlayer;

    public UpdateSinglePlayerHand(int indexPlayer, DiceStack handPlayer) {
        this.indexPlayer = indexPlayer;
        this.handPlayer = handPlayer;
    }

    public DiceStack getHandPlayer() {
        return handPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}