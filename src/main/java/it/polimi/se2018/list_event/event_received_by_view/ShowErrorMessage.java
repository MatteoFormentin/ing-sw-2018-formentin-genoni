package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventController, describe the event "login" produced by the view
 *
 * @author Luca Genoni
 */
public class ShowErrorMessage extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    private String errorMessage; //for simple use in the view
    private Exception typeException; //for a complex use in the view

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Exception getTypeException() {
        return typeException;
    }

    public void setTypeException(Exception typeException) {
        this.typeException = typeException;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
