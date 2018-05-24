package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Pennello per Eglomise.
 * <p>
 * Description
 * Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.
 * Devi rispettare tutte le altre restrizioni di piazzamento
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class PennelloEglomise extends ToolCard {
    public PennelloEglomise() {
        super();
        super.setId(1);
        super.setName("Pennello per Eglomise");
        super.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore.\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
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
            return false; // before Place your dice in Hand ಠ_ಠ troller OOOOOOOOOOOOOOOOOOOOOOOOOOOr if you have 1 dice in hand that's the one with the effect of this card
        saveUsed(gameBoard, indexPlayer, indexOfCardInGame);
        return true; //no immediate effect it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }
}//
// controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
//----------------------------->controller
/*      view.askTheCellOfWindowForToolCard()
        wait response
        control.handleCoordinateWindowForToolCard(row,column)(for this card diceFromWindowToHand)
        if false view.showMessage("Error wrong index") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Special End Turn ಠ_ಠ i hate you stupid player

        view.askTheCellOfWindowForToolCard() normal call for the placing
        control.handleCoordinateWindow(row,column)   Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento
        if false view.showMessage("Error") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out ಠ_ಠ i hate you stupid player ??? what should i do with your stupid dice? let me think about it
         */