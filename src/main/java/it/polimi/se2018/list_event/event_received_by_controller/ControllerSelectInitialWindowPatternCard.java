package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, the controller receives the selected window's index of the initial windows
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectInitialWindowPatternCard extends EventController {
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
