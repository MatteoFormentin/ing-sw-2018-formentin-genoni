package it.polimi.se2018.list_event.event_received_by_view.event_from_model.update_game;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventClientFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;

/**
 * Extends EventClient, updates the favor Tokens and the points of one player
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSinglePlayerToken extends EventClientFromModel {
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