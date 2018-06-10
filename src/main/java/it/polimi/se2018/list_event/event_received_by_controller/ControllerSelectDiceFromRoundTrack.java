package it.polimi.se2018.list_event.event_received_by_controller;

/**
 * Extends EventController, the controller receives the selected die from the roundTrack by the player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class ControllerSelectDiceFromRoundTrack extends EventController {
    private int round;
    private int index;

    public ControllerSelectDiceFromRoundTrack(int round, int index) {
        this.round = round;
        this.index = index;
    }

    public int getRound() {
        return round;
    }

    public int getIndex() {
        return index;
    }

    public void accept(ControllerVisitor visitor) {
        visitor.visit(this);
    }

}
