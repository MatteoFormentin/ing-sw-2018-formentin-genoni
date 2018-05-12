package it.polimi.se2018.event.list_event;

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
}
