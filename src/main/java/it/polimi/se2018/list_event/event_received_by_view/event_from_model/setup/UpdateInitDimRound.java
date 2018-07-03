package it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventViewFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;
import it.polimi.se2018.model.dice.DiceStack;

/**
 * Extends EventView, updates the initial length of the RoundTrack
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateInitDimRound extends EventViewFromModel {
    private DiceStack[] roundTrack;

    public UpdateInitDimRound(DiceStack[] roundTrack) {
        this.roundTrack = roundTrack;
    }

    public DiceStack[] getRoundTrack() {
        return roundTrack;
    }

    public DiceStack getRoundTrack(int index) {
        return roundTrack[index];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}