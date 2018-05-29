package it.polimi.se2018.list_event.event_view;

import it.polimi.se2018.list_event.event_controller.VisitorEventFromController;

/**
 * Extends EventView, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class EndTurn extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;

    @Override
    public void accept(VisitorEventFromView visitor) {
        visitor.visit(this);
    }

}
