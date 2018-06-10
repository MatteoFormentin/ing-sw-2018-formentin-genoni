package it.polimi.se2018.list_event.event_received_by_view;


/**
 * Visitor Pattern for the event received by the view.
 * all the method are public by default, it's an interface
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public interface ViewVisitor {

    //**********************************************from Controller**************************************
    //for the setup
    void visit(StartGame event);
    void visit(JoinGame event);

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
    void visit(SelectDiceFromRoundTrack event);
    void visit(SelectDiceFromDraftPool event);
    void visit(SelectToolCard event);
    void visit(MoveTimeoutExpired event);
    void visit(WaitYourTurn event);

    //for the end game
    void visit(EndGame event);

    //**********************************************from Model**************************************

    //setup update
    void visit(UpdateInitDimRound event);
    void visit(UpdateAllToolCard event);
    void visit(UpdateAllPublicObject event);
    void visit(UpdateSinglePrivateObject event);
    void visit(UpdateInitialWindowPatternCard event);
    void visit(UpdateSingleWindow event);

    //during the game
    void visit(UpdateSinglePlayerHand event);
    void visit(UpdateSingleCell event);
    void visit(UpdateDicePool event);

    void visit(UpdateSinglePlayerTokenAndPoints event);
    void visit(UpdateSingleToolCardCost event);

     void visit(UpdateSingleTurnRoundTrack event);

}
