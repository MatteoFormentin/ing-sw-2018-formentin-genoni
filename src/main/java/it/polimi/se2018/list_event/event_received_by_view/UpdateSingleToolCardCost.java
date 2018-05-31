package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.tool_card.ToolCard;

/**
 * forse inutile meglio fare l'update solo del costo della toolcard
 */
public class UpdateSingleToolCardCost extends EventView {

    private int indexToolCard;
    private int costToolCard;

    public UpdateSingleToolCardCost(int indexToolCard, int costToolCard) {
        this.indexToolCard = indexToolCard;
        this.costToolCard = costToolCard;
    }

    public int getIndexToolCard() {
        return indexToolCard;
    }

    public void setIndexToolCard(int indexToolCard) {
        this.indexToolCard = indexToolCard;
    }

    public int getCostToolCard() {
        return costToolCard;
    }

    public void setCostToolCard(int costToolCard) {
        this.costToolCard = costToolCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
