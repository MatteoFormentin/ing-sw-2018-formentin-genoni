package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

/**
 * Extends EventView, updates the favor Tokens and the points of one player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSinglePlayerToken extends EventViewFromModel {
    private int indexInGame;
    private int favorToken;

    public UpdateSinglePlayerToken(int indexInGame, int favorToken) {
        this.indexInGame = indexInGame;
        this.favorToken = favorToken;
    }

    public int getIndexInGame() {
        return indexInGame;
    }

    public int getFavorToken() {
        return favorToken;
    }


    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}