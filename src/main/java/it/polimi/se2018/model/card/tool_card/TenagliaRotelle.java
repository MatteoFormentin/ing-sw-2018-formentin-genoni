package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Tenaglia a Rotelle.
 * <p>
 * Description
 * Dopo il tuo primo turno scegli immediatamente un altro dado
 * Salta il tuo secondo turno in questo round
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class TenagliaRotelle extends ToolCard {
    public TenagliaRotelle() {
        super();
        super.setId(7);
        super.setName("Tenaglia a Rotelle");
        super.setDescription("Dopo il tuo primo turno scegli immediatamente un altro dado\n" +
                "Salta il tuo secondo turno in questo round");
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
        if(!noPreCondition(gameBoard, indexPlayer)) return false;
        // ಠ_ಠ troller place your dice in hand or should i put it in Draftpool??? :D muahmuahmuahmuahmuagmuag
       // if(gameBoard.endSpecialFirstTurn(indexPlayer));
        saveUsed(gameBoard, indexPlayer, indexOfCardInGame);
        return true; //no immediate effect it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}
// controller.useToolCard(gameBoard.getCostToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askDiceFromDraftPool()
        wait response
        control.handleDiceFromDraftPool(index DiceStack)
        if false view.showMessage("Error, dice don't exist") ಠ_ಠ
        if true modify model and model.notifyTheView() dice from draftpool to hand
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player

        view.askTheCellOfWindowForToolCard()
        wait response
        control.handleCoordinateWindow(row,column)
        if false view.showMessage("Error Place you dice better") ಠ_ಠ
        if true modify model and model.notifyTheView() place dice
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player
*/


