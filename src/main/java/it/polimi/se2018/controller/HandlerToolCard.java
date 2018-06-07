package it.polimi.se2018.controller;

import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.model.GameBoard;

import java.util.LinkedList;

public class HandlerToolCard implements ControllerVisitor {

    private GameBoard gameBoard;
    private Controller controller;
    //TODO lista di effetti da eseguire con linked list se avviene con successo aumentare il contatore degli effetti(se rimuovi non puoi fare undo)
    private LinkedList<Object> effectList;
    private int counterEffect;

    //TODO quando carica la toolcard preleva la lista di effetti da eseguire

    //TODO riceve l'event view Use Toolcard processa e a seconda della toolcard presente

    public HandlerToolCard(GameBoard gameBoard,Controller controller) {
        this.gameBoard = gameBoard;
        this.controller =controller;
        counterEffect=0;
    }
    public void sendEventToHandlerToolCard(EventController event) {
        event.accept(this);
    }
    @Override
    public void visit(EndTurnController event) {

    }


    @Override
    public void visit(SelectCellOfWindowController event) {

    }

    @Override
    public void visit(SelectDiceFromDraftpoolController event) {

    }

    @Override
    public void visit(SelectToolCardController event) {
        //TODO accetta la carta selezionata, carica gli effetti della carta Con un metodo get effect (riceve una linkedList di effetti)
    }

    @Override
    public void visit(SelectDiceFromHandController event) {

    }

    @Override
    public void visit(SelectDiceFromRoundTrackController event) {

    }


    @Override
    public void visit(SelectInitialWindowPatternCardController event) {

    }

}
