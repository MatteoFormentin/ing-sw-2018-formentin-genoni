package it.polimi.se2018.list_event.event_received_by_view.event_from_model;


import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;

/**
 * Visitor Pattern for the event received by the view.
 * all the method are public by default, it's an interface
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public interface ViewModelVisitor {
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

    void visit(UpdateInfoCurrentTurn event);

    void visit(UpdateSinglePlayerToken event);

    void visit(UpdateSingleToolCardCost event);

    void visit(UpdateSingleTurnRoundTrack event);

    void visit(UpdateCurrentPoint event);

    //endgame
    void visit(UpdateStatPodium event);

    //info other player
    void visit(UpdateDisconnection event);

    void visit(UpdatePlayerConnection event);


}
