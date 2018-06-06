package it.polimi.se2018.list_event.event_received_by_view;

import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;

/**
 * used only for the init and it's personalized
 */
public class UpdateAllPublicObject extends EventView{

    private ObjectivePublicCard[] publicCards;

    public UpdateAllPublicObject(ObjectivePublicCard[] publicCards) {
        this.publicCards = publicCards;
    }

    public ObjectivePublicCard[] getPublicCards() {
        return publicCards;
    }

    public ObjectivePublicCard getPublicCards(int index) {
        return publicCards[index];
    }
    public void setPublicCards(ObjectivePublicCard[] publicCards) {
        this.publicCards = publicCards;
    }

    public void accept(ViewVisitor visitor) {
        visitor.visit(this);
    }

}
