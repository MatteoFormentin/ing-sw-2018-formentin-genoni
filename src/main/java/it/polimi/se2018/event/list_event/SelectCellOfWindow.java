package it.polimi.se2018.event.list_event;

/**
 * Extends Event, describe the event "select a cell of window" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectCellOfWindow extends Event  {
    int line;
    int column;
}
