package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

/**
 * Extends EventView, updates the initial windows Pattern
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateInitialWindowPatternCard extends EventViewFromModel {
    private WindowPatternCard[] initialWindowPatternCard;

    public UpdateInitialWindowPatternCard(WindowPatternCard[] initialWindowPatternCard) {
        this.initialWindowPatternCard = initialWindowPatternCard;
    }

    public WindowPatternCard[] getInitialWindowPatternCard() {
        return initialWindowPatternCard;
    }
    public WindowPatternCard getInitialWindowPatternCard(int i) {
        return initialWindowPatternCard[i];
    }

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
