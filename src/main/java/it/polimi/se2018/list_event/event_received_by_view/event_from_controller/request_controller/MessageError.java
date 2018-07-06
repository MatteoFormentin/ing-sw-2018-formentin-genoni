package it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;

/**
 * Extends EventClient, tells the view to show an error message
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public class MessageError extends EventClientFromController {
    private String message;
    private boolean showMenuTurn;
    private boolean initGame;

    /**
     * constructor for an error message during the game
     *
     * @param message      the string to forward
     * @param showMenuTurn if the view need to show the turn menu
     */
    public MessageError(String message, boolean showMenuTurn) {
        this.message = message;
        this.showMenuTurn = showMenuTurn;
        initGame = false;
    }

    /**
     * constructor for error message, let choose if it's during the game or not(init or end).
     *
     * @param message      the string to forward
     * @param showMenuTurn if the view need to show the turn menu
     * @param initGame     if the message is
     */
    public MessageError(String message, boolean showMenuTurn, boolean initGame) {
        this.message = message;
        this.showMenuTurn = showMenuTurn;
        this.initGame = initGame;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShowMenuTurn() {
        return showMenuTurn;
    }

    public boolean isInitGame() {
        return initGame;
    }

    @Override
    public void acceptControllerEvent(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

}
