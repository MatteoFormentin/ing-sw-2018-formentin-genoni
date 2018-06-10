package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, updates the cost of the tool card
 *
 * @author Luca Genoni
 * @author Matteo Formentin
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

    public int getCostToolCard() {
        return costToolCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
