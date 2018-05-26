package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Tampone Diamantato.
 * <p>
 * Description
 * Dopo aver scelto un dado, giralo sulla faccia opposta
 * 6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class TamponeDiamantato extends ToolCard {
    public TamponeDiamantato() {
        super();
        super.setId(9);
        super.setName("Tampone Diamantato");
        super.setDescription("Dopo aver scelto un dado, giralo sulla faccia opposta \n" +
                "6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
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
        if(!preConditionOfDicePool(gameBoard, indexPlayer)) return false;
        saveUsed(gameBoard, indexPlayer, indexOfCardInGame);
        if(!gameBoard.oppositeFaceDice(indexPlayer))return false;
        //immediate effect also end here so you should notify the views when you come back... deal with that :/
        return true;
    }
}
