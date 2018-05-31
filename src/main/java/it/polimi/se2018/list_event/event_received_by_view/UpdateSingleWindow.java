package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

public class UpdateSingleWindow extends EventView  {
    private int indexPlayer;
    private WindowPatternCard windowPatternCard;

    public UpdateSingleWindow(int indexPlayer, WindowPatternCard windowPatternCard) {
        this.indexPlayer = indexPlayer;
        this.windowPatternCard = windowPatternCard;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }

    public void setWindowPatternCard(WindowPatternCard windowPatternCard) {
        this.windowPatternCard = windowPatternCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}