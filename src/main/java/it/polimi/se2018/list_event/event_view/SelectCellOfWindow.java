package it.polimi.se2018.list_event.event_view;

import it.polimi.se2018.list_event.event_controller.VisitorEventFromController;

/**
 * Extends EventView, describe the event "select a cell of window" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectCellOfWindow extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    int line;
    int column;

    public void accept(VisitorEventFromView visitor) {
        visitor.visit(this);
    }

}
