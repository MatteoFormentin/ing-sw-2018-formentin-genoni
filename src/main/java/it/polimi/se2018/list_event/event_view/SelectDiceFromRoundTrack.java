package it.polimi.se2018.list_event.event_view;

import it.polimi.se2018.list_event.event_controller.VisitorEventFromController;

/**
 * Extends EventView, describe the event "select dice from the round track" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromRoundTrack extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    int round;
    int index;

    public void accept(VisitorEventFromView visitor) {
        visitor.visit(this);
    }

}
