package it.polimi.se2018.list_event.event_received_by_controller;


import it.polimi.se2018.list_event.event_received_by_view.SelectDiceFromDraftpool;

public interface ControllerVisitor {

    /*

    {
        if (event instanceof StartGame) {
            //TODO controllre se id pacchetto presente nel server
            init();
        }


        if (event instanceof InsertDiceController) {
            // gameBoard.
        }

        if (event instanceof EndTurnController) {
            System.out.println("Arrivato pacchetto");
            // gameBoard.nextPlayer(event.getPlayerId());
            //EventController packet = new EndTurnController();
        }

        if (event instanceof UseToolCardController) {

        }

        if (event instanceof SelectWindow) {
            System.out.println("Arrivato SelectWindow da " + event.getPlayerId());
        }

        if (event instanceof Logout) {
            System.out.println("Arrivato Logout da " + event.getPlayerId());
        }

        if (event instanceof SelectInitialWindowPatternCardController) {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), ((SelectInitialWindowPatternCardController) event).getSelectedIndex());
        }

    }*/

    public void visit(EndTurnController event);

    public void visit(SelectCellOfWindowController event);

    public void visit(SelectDiceFromDraftpoolController event);

    public void visit(SelectToolCardController event);

    public void visit(SelectDiceFromHandController event);

    public void visit(SelectDiceFromRoundTrackController event);

    public void visit(SelectInitialWindowPatternCardController event);


}
