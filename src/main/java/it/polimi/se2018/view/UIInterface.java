package it.polimi.se2018.view;

import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;

public interface UIInterface {

    //From model
    public void updatePlayer(Player player);

    public void updateDicePool(DiceStack dicePool);

    public void updateRoundTrack(DiceStack[] roundTrack);
    public void updatePublicCard(ObjectivePublicCard[] publicCard);

    public void updateToolCard(ToolCard[] toolCard);

    public void updateOpponentPlayer(Player[] opponentPlayers);

    public void showMessage(EventView eventView);

    //From Controller
}
