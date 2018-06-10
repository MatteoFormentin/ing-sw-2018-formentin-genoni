package it.polimi.se2018.list_event.event_received_by_view;

/**
 * Extends EventView, updates the favor Tokens and the points of one player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSinglePlayerTokenAndPoints extends EventView  {
    private int indexInGame;
    private int favorToken;
    private int points;

    public UpdateSinglePlayerTokenAndPoints(int indexInGame, int favorToken, int points) {
        this.indexInGame = indexInGame;
        this.favorToken = favorToken;
        this.points = points;
    }

    public int getIndexInGame() {
        return indexInGame;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public int getPoints() {
        return points;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}