package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;

/**
 * used only for the init and it's personalized
 */
public class UpdateSinglePrivateObject {
    private int indexPlayer;
    private ObjectivePrivateCard privateCard;

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public ObjectivePrivateCard getPrivateCard() {
        return privateCard;
    }

    public void setPrivateCard(ObjectivePrivateCard privateCard) {
        this.privateCard = privateCard;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }
}
