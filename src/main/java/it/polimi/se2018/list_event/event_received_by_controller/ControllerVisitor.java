package it.polimi.se2018.list_event.event_received_by_controller;


/**
 * Visitor Pattern for the event received by the controller.
 * all the method are public by default, it's an interface.
 *
 * @author Luca Genoni
 * @author Matteo Formentin
 */
public interface ControllerVisitor {

    //for the init game choice
    void visit(ControllerSelectInitialWindowPatternCard event);

    //for the 3 move of the player
    void visit(ControllerMoveDrawAndPlaceDie event);
    void visit(ControllerMoveUseToolCard event);
    void visit(ControllerEndTurn event);

    //for the info required
    void visit(ControllerSelectCellOfWindow event);
    void visit(ControllerSelectDiceFromDraftPool event);
    void visit(ControllerSelectToolCard event);
    void visit(ControllerSelectDiceFromHand event);
    void visit(ControllerSelectDiceFromRoundTrack event);


}
