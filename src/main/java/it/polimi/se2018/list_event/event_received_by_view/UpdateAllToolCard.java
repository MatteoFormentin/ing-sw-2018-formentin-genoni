package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;

public class UpdateAllToolCard {

    private ToolCard[] toolCards;

    public ToolCard[] getPublicCards() {
        return toolCards;
    }

    public void setPublicCards(ToolCard[] toolCards) {
        this.toolCards = toolCards;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
