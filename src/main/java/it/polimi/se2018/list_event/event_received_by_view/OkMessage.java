package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Class
 * Extends EventController, describe the event "re-login" produced by the view.
 *
 * @author DavideMammarella
 */
public class OkMessage extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;

    String messageConfirm;
    boolean showTurn;

    public OkMessage(String messageConfirm, boolean showTurn) {
        this.messageConfirm = messageConfirm;
        this.showTurn = showTurn;
    }

    public String getMessageConfirm() {
        return messageConfirm;
    }

    public void setMessageConfirm(String messageConfirm) {
        this.messageConfirm = messageConfirm;
    }

    public boolean isShowTurn() {
        return showTurn;
    }

    public void setShowTurn(boolean showTurn) {
        this.showTurn = showTurn;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
