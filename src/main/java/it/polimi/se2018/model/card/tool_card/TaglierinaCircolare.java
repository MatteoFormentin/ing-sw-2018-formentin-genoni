package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card utensile Taglierina Circolare.
 * <p>
 * Description
 * Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class TaglierinaCircolare extends ToolCard {
    public TaglierinaCircolare() {
        super();
        super.setId(4);
        super.setName("Taglierina Circolare");
        super.setDescription("Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
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
// controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askDiceInRoundTrack()
        wait response
        control.handleRoundTrackForToolCard(indexRound, index DiceStack) ??? it's needed one method for the select and one for the change? mmmm don't know
        if false view.showMessage("Error, dice don't exist") ಠ_ಠ
        if true modify model and model.notifyTheView() change the 2 dice (in hand and the selected)
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player
*/

