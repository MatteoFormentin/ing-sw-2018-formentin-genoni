package it.polimi.se2018.list_event.event_view;

/**
 * Extends EventController, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromDraftpool extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
