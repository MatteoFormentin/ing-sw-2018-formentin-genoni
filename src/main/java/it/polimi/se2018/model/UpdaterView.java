package it.polimi.se2018.model;

import it.polimi.se2018.alternative_network.newserver.GameRoom;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.UpdateAllPublicObject;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.dice.DiceColor;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.model.dice.FactoryDice;
import it.polimi.se2018.network.server.ServerController;

public class UpdaterView {
    private GameBoard gameBoard;
    private ServerController server;
    private GameRoom server2;

    public void updateObjectivePublicCard() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) {
            UpdateAllPublicObject packet = new UpdateAllPublicObject(gameBoard.getObjectivePublicCard());
            packet.setPlayerId(i);
            if (server == null) server2.sendEventToView(packet);
            else server.sendEventToView(packet);

        }
    }

    public void updateObjectivePublicCard(int indexPlayer) {

        UpdateAllPublicObject packet = new UpdateAllPublicObject(gameBoard.getObjectivePublicCard());
        packet.setPlayerId(indexPlayer);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);

    }

    private int currentRound;//can have only the get
    private int currentTurn;//can have only the get
    private int indexCurrentPlayer;//can have only the get
    private boolean stopGame;//can have only the get
    private int countSetWindow;
    private DiceStack[] roundTrack;//can have only the get
    private Player[] player; //can have only the get(they are created with the constructor )
    private ToolCard[] toolCard;//can have only the get
    private ObjectivePublicCard[] objectivePublicCard;//can have only the get
    private DiceStack dicePool;//can have only the get
    private FactoryDice factoryDiceForThisGame; //nobody can see it
    private DiceColor colorRestriction;
}
