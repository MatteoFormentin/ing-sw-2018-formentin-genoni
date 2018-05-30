package it.polimi.se2018.list_event.event_view;

/**
 * Extends EventController, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectInitialWindowPatternCard extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
