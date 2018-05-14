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
    public boolean canPerform(GameBoard gameBoard, EventView event) {
        if (!(event instanceof EndTurn)) return false;
        return gameBoard.isEndGame();
    }

    /**
     * Method for set all the point accumulated
     *
     * @param gameBoard needs to be in state of endgame
     * @param event don't know
     */
    public void doMove(GameBoard gameBoard, EventView event) {
        int pointCounter;

        for (Player player : gameBoard.getPlayer()) {
            pointCounter = 0;
            //calculate point for the public object
            for (ObjectivePublicCard objectivePublicCard : gameBoard.getObjectivePublicCard()) {
                pointCounter += objectivePublicCard.calculatePoint(player.getPlayerWindowPattern());
            }
            //calculate point for the private object
            pointCounter += player.getPrivateObject().calculatePoint(player.getPlayerWindowPattern());
            //add the token left
            pointCounter += player.getFavorToken();
            //subtract the void cell
            pointCounter = pointCounter - (20 - player.getPlayerWindowPattern().getNumberOfCellWithDice());
            player.setPoints(pointCounter);
        }
    }
}
