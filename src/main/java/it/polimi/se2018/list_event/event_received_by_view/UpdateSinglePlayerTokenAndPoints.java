package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.Player;

/**
 * event for update the favor token and the point of the Player with the index contain in the event
 */
public class UpdateSinglePlayerTokenAndPoints {
    private int indexInGame;
    private int favorToken;
    private int points;

    public int getIndexInGame() {
        return indexInGame;
    }

    public void setIndexInGame(int indexInGame) {
        this.indexInGame = indexInGame;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}