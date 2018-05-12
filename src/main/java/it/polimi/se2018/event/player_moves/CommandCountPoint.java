package it.polimi.se2018.event.player_moves;

import it.polimi.se2018.event.list_event.EndTurn;
import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;

/**
 * Command for the end of the game calculate all the Point of each Player
 *
 * @author Luca Genoni
 */
public class CommandCountPoint implements ICommandPlayerMove {
    public boolean canPerform(GameBoard gameBoard, EventView event){
        if (!(event instanceof EndTurn)) return false;
        return gameBoard.isEndGame();
    }

    public void doMove(GameBoard gameBoard, EventView event){
        int pointCounter;

        for (Player player : gameBoard.getPlayer()) {
            pointCounter=0;
            for(ObjectivePublicCard objectivePublicCard: gameBoard.getObjectivePublicCard()){
                pointCounter=pointCounter+objectivePublicCard.calculatePoint(player.getPlayerWindowPattern());
            }
            pointCounter=player.getPrivateObject().calculatePoint(player.getPlayerWindowPattern());
            player.setPoints(pointCounter);
        }
    }
}
