package it.polimi.se2018.event.list_event;

/**
 * Extends EventView, describe the event "select dice from the draft pool" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectDiceFromDraftpool extends EventView {
    //from EventView private String nicknamPlayer;
    //from EventView private Model model;
    int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
