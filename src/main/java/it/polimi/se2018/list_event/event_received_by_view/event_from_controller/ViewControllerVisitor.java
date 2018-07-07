package it.polimi.se2018.list_event.event_received_by_view.event_from_controller;

import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskNewGame;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.ConnectionDown;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.LoginResponse;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;

public interface ViewControllerVisitor {


    //***************************************From server ***************************************
    void visit(LoginResponse event);

    void visit(ConnectionDown event);

    void visit(AskLogin event);

    void visit(AskNewGame event);

    //**********************************************from Controller**************************************
    //for the setup
    void visit(StartGame event);

    //for the initial prt of the game
    void visit(ShowAllCards event);

    void visit(SelectInitialWindowPatternCard event);

    void visit(InitialEnded event);

    //respond messages
    void visit(MessageError event);

    void visit(MessageOk event);

    //for the turn
    void visit(StartPlayerTurn event);

    void visit(SelectCellOfWindow event);

    void visit(SelectDiceFromDraftPool event);

    void visit(SelectToolCard event);

    void visit(MoveTimeoutExpired event);

    void visit(WaitYourTurn event);

    //for tool card
    void visit(SelectDiceFromRoundTrack event);

    void visit(SelectValueDice event);

    void visit(SelectIncrementOrDecreaseDice event);

    //for the end game
    void visit(EndGame event);
}
