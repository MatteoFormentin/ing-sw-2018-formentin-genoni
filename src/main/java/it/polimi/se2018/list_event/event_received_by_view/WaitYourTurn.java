package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventController, describe the event "end of the turn" produced by the view
 *
 * @author Luca Genoni
 */
public class WaitYourTurn extends EventView {
    //from EventController private String nicknamPlayer;
    //from EventController private Model model;
    private int indexCurrentPlayer;

    public WaitYourTurn(int indexCurrentPlayer) {
        this.indexCurrentPlayer = indexCurrentPlayer;
    }

    public int getIndexCurrentPlayer() {
        return indexCurrentPlayer;
    }

    public void setIndexCurrentPlayer(int indexCurrentPlayer) {
        this.indexCurrentPlayer = indexCurrentPlayer;
    }

    @Override
    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
