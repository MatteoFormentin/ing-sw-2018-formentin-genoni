package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;

/**
 * Extends EventView, tells the view which player is playing
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class WaitYourTurn extends EventViewFromController {
    private int indexCurrentPlayer;

    public WaitYourTurn(int indexCurrentPlayer) {
        this.indexCurrentPlayer = indexCurrentPlayer;
    }

    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
