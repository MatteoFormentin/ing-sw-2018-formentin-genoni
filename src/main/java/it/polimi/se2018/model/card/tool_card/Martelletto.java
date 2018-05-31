package it.polimi.se2018.model.card.tool_card;


import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Martelletto.
 * <p>
 * Description
 * Tira nuovamente tutti i dadi della Riserva
 * Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class Martelletto extends ToolCard {
    public Martelletto() {
        super();
        super.setId(6);
        super.setName("Martelletto");
        super.setDescription("Tira nuovamente tutti i dadi della Riserva\n" +
                "Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado");
    }

    /**
     * card check
     *
     * @param gameBoard         where the card is used
     * @param indexPlayer          of the player who use the card
     * @param indexOfCardInGame 0,1,2 needed to change the Flag true/false first USe ?????????????????? maybe better in GameBoard......
     * @return true if the toolcard has been activated, false otherwise
     */
    public boolean effect(GameBoard gameBoard, int indexPlayer, int indexOfCardInGame) {
        if (!noPreCondition(gameBoard, indexPlayer)) return false;
        if (gameBoard.getPlayer(indexPlayer).isHasDrawNewDice())
            return false; // before Place your dice in Hand ಠ_ಠ troller
        if (!gameBoard.getPlayer(indexPlayer).isFirstTurn())
            return false; // you can't use it in the first turn ಠ_ಠ troller
        saveUsed(gameBoard, indexPlayer, indexOfCardInGame);
        gameBoard.getDicePool().reRollAllDiceInStack();
        return true; //immediate effect also end here so you should notify the views when you come back... it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}
