package it.polimi.se2018.list_event.event_view;

/**
 * Extends EventController, describe the event "select a cell of window" produced by the view
 *
 * @author Luca Genoni
 */
public class SelectCellOfWindow extends EventController {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    int line;
    int column;

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
