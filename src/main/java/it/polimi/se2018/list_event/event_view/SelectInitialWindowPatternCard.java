package it.polimi.se2018.list_event.event_view;

import it.polimi.se2018.list_event.event_controller.VisitorEventFromController;

/**
 * Extends EventView, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectInitialWindowPatternCard extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void accept(VisitorEventFromView visitor) {
        visitor.visit(this);
    }

}
