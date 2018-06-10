package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, asks the view to select a Dice from dicePool
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class SelectDiceFromDraftPool extends EventView {

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
