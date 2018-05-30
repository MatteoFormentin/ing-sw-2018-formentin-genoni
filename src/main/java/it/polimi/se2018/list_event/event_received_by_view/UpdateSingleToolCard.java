package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.tool_card.ToolCard;

/**
 * forse inutile meglio fare l'update solo del costo della toolcard
 */
public class UpdateSingleToolCard {

    private int indexToolCard;
    private ToolCard toolCard;

    public int getIndexToolCard() {
        return indexToolCard;
    }

    public void setIndexToolCard(int indexToolCard) {
        this.indexToolCard = indexToolCard;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public void setToolCard(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
