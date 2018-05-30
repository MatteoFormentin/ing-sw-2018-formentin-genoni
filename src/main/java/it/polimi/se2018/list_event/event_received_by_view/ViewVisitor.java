package it.polimi.se2018.list_event.event_received_by_view;


public interface ViewVisitor {

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

    public void visit(EndGame event);

    public void visit(InitialWindowPatternCard event);

    public void visit(StartGame event);

    public void visit(StartPlayerTurn event);

    public void visit(WaitYourTurn event);
    public void visit(SelectCellOfWindowView event);
    public void visit(SelectDiceFromDraftpool event);
    public void visit(SelectToolCard event);
    public void visit(ShowErrorMessage event);

}
