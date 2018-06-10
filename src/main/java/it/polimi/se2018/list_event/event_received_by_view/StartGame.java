package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, tells the view the name of the players and their number.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class StartGame extends EventView {

    private String[] playersName;

    public String[] getPlayersName() {
        return playersName;
    }
    public String getPlayersName(int i) {
        return playersName[i];
    }
    public void setPlayersName(String[] playersName) {
        this.playersName = playersName;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
