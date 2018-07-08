package it.polimi.se2018.list_event.event_received_by_view.event_from_model.update_game;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

/**
 * Extends EventClient, updates a single window
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSingleWindow extends EventClientFromModel {
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

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}