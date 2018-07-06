package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

/**
 * Extends EventClient, updates all the tool cards
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateStatPodium extends EventClientFromModel {

    private int[][] sortedPlayer;
    private String[] description;

    public UpdateStatPodium(int[][] sortedPlayer, String[] description) {
        this.sortedPlayer = sortedPlayer;
        this.description = description;
    }

    public String getDescription(int index) {
        return description[index];
    }

    public int[][] getSortedPlayer() {
        return sortedPlayer;
    }

    public int[] getOneSortedPlayer(int index) {
        return sortedPlayer[index];
    }

    /**
     * @param index     the number in the podium
     * @param indexInfo in 0 there is the id of the player, 1 there is the sum of the points, the other are the detailed points
     * @return the int requested
     */
    public int getOneSortedPlayerInfo(int index, int indexInfo) {
        return sortedPlayer[index][indexInfo];
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
