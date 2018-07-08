package it.polimi.se2018.list_event.event_received_by_view.event_from_model.update_game;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventClient, updates the cost of the tool card
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSingleToolCardCost extends EventClientFromModel {

    private int indexToolCard;
    private int costToolCard;

    public UpdateSingleToolCardCost(int indexToolCard, int costToolCard) {
        this.indexToolCard = indexToolCard;
        this.costToolCard = costToolCard;
    }

    public int getIndexToolCard() {
        return indexToolCard;
    }

    public int getCostToolCard() {
        return costToolCard;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
