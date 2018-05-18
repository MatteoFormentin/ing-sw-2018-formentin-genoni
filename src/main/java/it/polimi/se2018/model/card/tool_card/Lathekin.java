package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Lathekin.
 * <p>
 * Description
 * Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class Lathekin extends ToolCard {
    public Lathekin() {
        super();
        super.setId(3);
        super.setName("Lathekin");
        super.setDescription("Muovi  esattamente  due  dadi,  rispettando  tutte  le  restrizioni  di  piazzamento");
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
        if (!noPreCondition(gameBoard, idPlayer)) return false;
        if (gameBoard.getPlayer(idPlayer).isHasDrawNewDice())
            return false; // before Place your dice in Hand ಠ_ಠ troller OOOOOOOOOOOOOOOOOOOOOOOOOOOr if you have 1 dice in hand you can only pick up one more dice
        saveUsed(gameBoard, idPlayer, indexOfCardInGame);
        return true; //no immediate effect it's too cool if return a number/string and the controller parsing this information know how to handle the card
    }

}
// controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame))
//----------------------------->controller
/*
       view.askTheCellOfWindowForToolCard() x2 times consecutive you can't use while or for ಠ_ಠ use a counter 1th dice and then 2nd dice
        wait response
        control.handleCoordinateWindowForToolCard(row,column)(for this card diceFromWindowToHand)
        if false view.showMessage("Error wrong index") ಠ_ಠ
        if true modify model and model.notifyTheView()
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