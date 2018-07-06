package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventClient, tells the view that the last move was ok.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class MessageOk extends EventClientFromController {

    private String messageConfirm;
    private boolean showTurnMenu;
    private boolean initGame;

    /**
     * constructor for ok message during the game.
     *
     * @param messageConfirm the string to forward
     * @param showTurnMenu   if the view need to show the turn menu
     */
    public MessageOk(String messageConfirm, boolean showTurnMenu) {
        this.messageConfirm = messageConfirm;
        this.showTurnMenu = showTurnMenu;
        initGame = false;
    }

    /**
     * constructor for ok message, let choose if it's during the game or not(init or end).
     *
     * @param messageConfirm the string to forward
     * @param showTurnMenu   if the view need to show the turn menu
     * @param initGame       if the message is
     */
    public MessageOk(String messageConfirm, boolean showTurnMenu, boolean initGame) {
        this.messageConfirm = messageConfirm;
        this.showTurnMenu = showTurnMenu;
        this.initGame = initGame;
    }

    /**
     * get the message to display
     *
     * @return the string of the message
     */
    public String getMessageConfirm() {
        return messageConfirm;
    }

    /**
     * get if the message is generated during the game
     *
     * @return false if the ok message id generated during the init or end game, true otherwise
     */
    public boolean isShowTurnMenu() {
        return showTurnMenu;
    }

    public boolean isInitGame() {
        return initGame;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
