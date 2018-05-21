package it.polimi.se2018.view;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;

public interface UIInterface {

    //From model
    public void updatePlayer(Player player);
    public void updateRoundTrack(DiceStack[] roundtrack);
    public void updatePublicCard(ObjectivePublicCard[] publicCard);
    public void updatePrivateCard(ObjectivePrivateCard[] privateCard);
    public void updateToolCard(ToolCard[] ToolCard);
    public void updateOpponentPlayer(Player[] opponentPlayer);

    //From Controller
}
