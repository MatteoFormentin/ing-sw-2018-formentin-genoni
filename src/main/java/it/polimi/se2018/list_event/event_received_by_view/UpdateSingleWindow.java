package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

/**
 * Extends EventView, updates a single window
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
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

    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}