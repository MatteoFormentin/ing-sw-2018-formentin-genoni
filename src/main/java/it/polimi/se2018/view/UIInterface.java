package it.polimi.se2018.view;

import it.polimi.se2018.list_event.event_controller.EventView;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.dice.DiceStack;

public interface UIInterface {

    //From model and controller
    public void showMessage(EventView eventView);
}
