package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;

public class UpdateAllToolCard extends EventView{

    private ToolCard[] toolCards;

    public UpdateAllToolCard(ToolCard[] toolCards) {
        this.toolCards = toolCards;
    }

    public ToolCard[] getToolCard() {
        return toolCards;
    }

    public void setToolCard(ToolCard[] toolCards) {
        this.toolCards = toolCards;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
