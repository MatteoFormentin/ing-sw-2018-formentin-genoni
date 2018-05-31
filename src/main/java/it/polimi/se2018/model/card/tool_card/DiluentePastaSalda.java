package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Diluente per Pasta Salda.
 * <p>
 * Description
 * Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto
 * Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack and DiceBag.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class DiluentePastaSalda extends ToolCard {
    public DiluentePastaSalda() {
        super();
        super.setId(10);
        super.setName("Diluente per Pasta Salda");
        super.setDescription("Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto\n" +
                "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento");
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
        // begin of the effect
      //  if(!gameBoard.changeDiceBetweenHandAndFactory(indexPlayer))return false;
        return true; //continue the effect (first you should notify the views) it's too cool if return a number/string and the controller parsing this information know how to handle the card

        // controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
        //----------------------------->controller
        /*view.askTheValueOfTheDice()
        wait response (if no response end turn) (dado rimesso nella DraftPool perchè nuovo Dado)
        control.handleSetNewValueForDice(value)
        if false view.showMessage("Error value of dice")ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player

        view.askTheCellOfWindow() normal call for the placing
        control.handleCoordinateWindow(row,column)   Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento
        if false view.showMessage("Error") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player
         */
    }

}
