package it.polimi.se2018.list_event.event_view;


public interface ViewVisitor {

    /*

    {
        if (event instanceof StartGame) {
            //TODO controllre se id pacchetto presente nel server
            init();
        }


        if (event instanceof InsertDice) {
            // gameBoard.
        }

        if (event instanceof EndTurn) {
            System.out.println("Arrivato pacchetto");
            // gameBoard.nextPlayer(event.getPlayerId());
            //EventController packet = new EndTurn();
        }

        if (event instanceof UseToolCard) {

        }

        if (event instanceof SelectWindow) {
            System.out.println("Arrivato SelectWindow da " + event.getPlayerId());
        }

        if (event instanceof Logout) {
            System.out.println("Arrivato Logout da " + event.getPlayerId());
        }

        if (event instanceof SelectInitialWindowPatternCard) {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), ((SelectInitialWindowPatternCard) event).getSelectedIndex());
        }

    }*/

    public void visit(EndGame event);

    public void visit(InitialWindowPatternCard event);

    public void visit(StartGame event);

    public void visit(StartPlayerTurn event);

    public void visit(WaitYourTurn event);


}
