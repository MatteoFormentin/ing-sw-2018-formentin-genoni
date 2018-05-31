package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Pinza Sgrossatrice.
 * <p>
 * Description
 * Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1.
 * Non puoi cambiare un 6 in 1 o un 1 in 6
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class PinzaSgrossatrice extends ToolCard {
    public PinzaSgrossatrice() {
        super();
        super.setId(0);
        super.setName("Pinza Sgrossatrice");
        super.setDescription("Dopo aver scelto un dado, aumenta o dominuisci il valore del dado scelto di 1\n" +
                "Non puoi cambiare un 6 in 1 o un 1 in 6");
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
        return true; //no immediate effect it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}
// controller.useToolCard(gameBoard.getCostToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askIncrease()
        wait response
        control.handleCoordinateWindowForToolCard(boolean true/false)(true for increase flase decrease)
        if false view.showMessage("Error, can't transform 6 in 1 or 1 in 6") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Special End Turn ಠ_ಠ i hate you stupid player
*/

