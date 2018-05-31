package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

/**
 * Extends EventController, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class InitialWindowPatternCard extends EventView  {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;


    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
