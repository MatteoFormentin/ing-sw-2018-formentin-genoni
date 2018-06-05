package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;

import java.util.LinkedList;

public class HandlerToolCard {

    private GameBoard gameBoard;
    private Controller controller;
    //TODO lista di effetti da eseguire con linked list se avviene con successo aumentare il contatore degli effetti(se rimuovi non puoi fare undo)
    private LinkedList<Object> effectList;
    private int counterEffect;
    public HandlerToolCard(GameBoard gameBoard,Controller controller) {
        this.gameBoard = gameBoard;
        this.controller =controller;
        counterEffect=0;
    }

    //TODO quando carica la toolcard preleva la lista di effetti da eseguire

    //TODO riceve l'event view Use Toolcard processa e a seconda della toolcard presente
}
