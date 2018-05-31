package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;

public class UpdateSinglePlayerHand extends EventView{
    private int indexPlayer;
    private DiceStack handPlayer;

    public UpdateSinglePlayerHand(int indexPlayer, DiceStack handPlayer) {
        this.indexPlayer = indexPlayer;
        this.handPlayer = handPlayer;
    }

    public DiceStack getHandPlayer() {
        return handPlayer;
    }

    public void setHandPlayer(DiceStack handPlayer) {
        this.handPlayer = handPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}