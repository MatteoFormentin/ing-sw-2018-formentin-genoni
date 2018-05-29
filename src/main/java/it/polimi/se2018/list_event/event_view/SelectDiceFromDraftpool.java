package it.polimi.se2018.list_event.event_view;

import it.polimi.se2018.list_event.event_controller.VisitorEventFromController;

/**
 * Extends EventView, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromDraftpool extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void accept(VisitorEventFromView visitor) {
        visitor.visit(this);
    }

}
