package it.polimi.se2018.list_event.event_received_by_view.event_from_model;

import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;

/**
 * Extends EventView, updates the single Private Object
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class UpdateSinglePrivateObject extends EventViewFromModel {
    private int indexPlayer;
    private ObjectivePrivateCard privateCard;

    public UpdateSinglePrivateObject(int indexPlayer, ObjectivePrivateCard privateCard) {
        this.indexPlayer = indexPlayer;
        this.privateCard = privateCard;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public ObjectivePrivateCard getPrivateCard() {
        return privateCard;
    }

    @Override
    public void acceptModelEvent(ViewModelVisitor visitor) {
        visitor.visit(this);
    }

}
