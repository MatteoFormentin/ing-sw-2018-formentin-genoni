package it.polimi.se2018.list_event.event_received_by_view.event_from_model;


import it.polimi.se2018.list_event.event_received_by_view.event_from_model.notify_connection.UpdatePlayerConnectionDuringGame;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.notify_connection.UpdatePlayerConnectionSetUp;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.update_game.*;

/**
 * Visitor Pattern for the event received by the view.
 * all the method are public by default, it's an interface
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public interface ViewModelVisitor {
    //**********************************************from Model**************************************

    //event before the setup and during
    void visit(UpdatePlayerConnectionDuringGame event);

    void visit(UpdatePlayerConnectionSetUp event);


    //setup update
    void visit(UpdateNamePlayers event);

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


}
