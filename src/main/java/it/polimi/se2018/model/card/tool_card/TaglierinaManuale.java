package it.polimi.se2018.model.card.tool_card;


import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Taglierina Manuale.
 * <p>
 * Description
 * Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round
 * Devi rispettare tutte le restrizioni di piazzamento
 * <p>
 * Require DiceStack.
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class TaglierinaManuale extends ToolCard {
    public TaglierinaManuale() {
        super();
        super.setId(11);
        super.setName("Taglierina Manuale");
        super.setDescription("Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round\n" +
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
        if(!noPreCondition(gameBoard, idPlayer)) return false;
        saveUsed(gameBoard, idPlayer, indexOfCardInGame);
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

        view.askTheCellOfWindowForToolCard()
        wait response
        control.handleCoordinateWindowForToolCard(row,column)(for this card diceFromWindowToHand)
        if false view.showMessage("Error wrong index or color not equals") ಠ_ಠ
        if true modify model and model.notifyTheView() pick up in hand
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player

        view.askTheCellOfWindowForToolCard()
        wait response
        control.handleCoordinateWindowForToolCard(row,column)(for this card diceFromWindowToHand)
        if false view.showMessage("Error wrong index or color not equals") ಠ_ಠ
        if true modify model and model.notifyTheView() pick up in hand
        if the timeout runs out Special End Turn ಠ_ಠ i hate you stupid player ??? what should i do with your stupid dice? let me think about it

        // ok dude now you can place your stupid dices where you want, ಠ_ಠ but not everywhere

        view.selectDiceInHand()
        control.selectDiceInHand(int index)
        if false view.showMessage("Error you don' have so many dice")
        if true modify model and model.notifyTheView() (the dice selected go in position 0 in the hand)
        if the timeout runs out Special End Turn ಠ_ಠ i hate you stupid player ??? what should i do with your stupid dice? let me think about it

        view.askTheCellOfWindowForToolCard() x2 times consecutive you can't use while or for ಠ_ಠ use the same counter 1th dice, 2nd dice
        control.handleSetNewValueForDice(value)
        if false view.showMessage("Error ")
        if true modify model and model.notifyTheView()
        if the timeout runs out Special End Turn ಠ_ಠ i hate you stupid player ??? what should i do with your stupid dice? let me think about it

*/

