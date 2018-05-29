package it.polimi.se2018.list_event.event_view;


import it.polimi.se2018.list_event.event_controller.EndGame;
import it.polimi.se2018.list_event.event_controller.InitialWindowPatternCard;
import it.polimi.se2018.list_event.event_controller.StartGame;

public interface VisitorEventFromView {

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
            //EventView packet = new EndTurn();
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

    public void visit(EndTurn event);

    public void visit(InsertDice event);

    public void visit(SelectCellOfWindow event);

    public void visit(SelectDiceFromDraftpool event);

    public void visit(SelectDiceFromHand event);

    public void visit(SelectDiceFromRoundTrack event);

    public void visit(UseToolCard event);


}
