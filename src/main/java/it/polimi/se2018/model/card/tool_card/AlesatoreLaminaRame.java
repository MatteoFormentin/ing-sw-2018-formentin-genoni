package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Tool card Alesatore lamina di rame.
 * <p>
 * Description
 * Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore
 * Devi rispettare tutte le altre restrizioni di piazzamento
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */
public class AlesatoreLaminaRame extends ToolCard {
    public AlesatoreLaminaRame() {
        super();
        super.setId(2);
        super.setName("Alesatore per lamina di rame");
        super.setDescription("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore\n" +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
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
    //controller.useToolCard(gameBoard.getToolCard(indexOfCardInGame)) call this effect
    //----------------------------->controller
        /*view.askTheCellOfWindowForToolCard()
        wait response (if no response end turn)
        control.handleCoordinateWindowForToolCard(row,column)(for this card diceFromWindowToHand)
        if false view.showMessage("Error wrong index") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out Normal End Turn (all dice in hand to the DraftPool) ಠ_ಠ i hate you stupid player

        view.askTheCellOfWindowForToolCard(), the same method before ಠ_ಠ the stupid view don't know the difference
        control.handleCoordinateWindowForToolCard(row,column)(Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore)
        if false view.showMessage("Error") ಠ_ಠ
        if true modify model and model.notifyTheView()
        if the timeout runs out then Destroy the Dice in Hand(don't put it in the DiceFactory) ಠ_ಠ i hate you stupid player
         */
}
