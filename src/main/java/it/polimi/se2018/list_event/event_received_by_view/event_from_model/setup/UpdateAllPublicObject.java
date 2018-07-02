package it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup;

import it.polimi.se2018.list_event.event_received_by_view.event_from_model.EventViewFromModel;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.ViewModelVisitor;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;

/**
 * Extends EventView, updates all the public cards
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateAllPublicObject extends EventViewFromModel {

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

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
