package it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;
import it.polimi.se2018.model.card.ToolCard;

/**
 * Extends EventClient, updates all the tool cards
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateAllToolCard extends EventClientFromModel {

    private ToolCard[] toolCards;

    public UpdateAllToolCard(ToolCard[] toolCards) {
        this.toolCards = toolCards;
    }

    public ToolCard[] getToolCard() {
        return toolCards;
    }
    public ToolCard getToolCard(int index) {
        return toolCards[index];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
