package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Riga in Sughero.
 * <p>
 * Description
 * Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado
 * Devi rispettare tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class RigaSughero extends ToolCard {
    public RigaSughero() {
        super();
        super.setId(8);
        super.setName("Riga in Sughero");
        super.setDescription("Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado\n" +
                "Devi rispettare tutte le restrizioni di piazzamento");
    }

    /**
     * card check
     *
     * @param gameBoard         where the card is used
     * @param idPlayer          of the player who use the card
     * @param indexOfCardInGame 0,1,2 needed to change the Flag true/false first USe ?????????????????? maybe better in GameBoard......
     * @return true if the toolcard has been activated, false otherwise
     */
    public boolean effect(GameBoard gameBoard, int idPlayer, int indexOfCardInGame) {
        if (!preConditionOfDicePool(gameBoard, idPlayer)) return false;
        saveUsed(gameBoard, idPlayer, indexOfCardInGame);
        return true; //no immediate effect it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}
// controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askIncrease()
        wait response
        control.handleCoordinateWindowForToolCard(boolean true/false)(true for increase flase decrease)
        if false view.showMessage("Error, can't transform 6 in 1 or 1 in 6") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player
*/

        /*
        Before:
        The Player Select One From DicePool(of the gameboard)
        ****** the Player use this card check state player need to have one dice in hand and canusedtoolcard==true
        inserDice AdjacentRestriction False
        end toolCard (reduce favor token of the player )
         */

