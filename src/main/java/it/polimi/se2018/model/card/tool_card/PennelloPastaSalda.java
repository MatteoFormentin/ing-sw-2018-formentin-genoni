package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Pennello per Pasta Salda.
 * <p>
 * Description
 * Dopo aver scelto un dado, tira nuovamente quel dado
 * Se non puoi piazzarlo, riponilo nella Riserva
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class PennelloPastaSalda extends ToolCard {
    public PennelloPastaSalda() {
        super();
        super.setId(5);
        super.setName("Pennello per Pasta Salda");
        super.setDescription("Dopo aver scelto un dado, tira nuovamente quel dado\n" +
                "Se non puoi piazzarlo, riponilo nella Riserva");
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
        gameBoard.getPlayer(indexPlayer).rollDiceInHand();
        return true; // immediate effect. it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}
    // controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askTheCellOfWindow()
        wait response
        control.handleCoordinateWindow(row,column)
        if false view.showMessage("Error") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player
*/


