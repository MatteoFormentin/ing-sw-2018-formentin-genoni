package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.tool_card.ToolCard;

/**
 * Extends EventView, updates all the tool cards
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateAllToolCard extends EventView {

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

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
