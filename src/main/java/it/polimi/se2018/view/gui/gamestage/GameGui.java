package it.polimi.se2018.view.gui.gamestage;

import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.view.UIInterface;

public class GameGui implements UIInterface,ViewVisitor {
    private static GameGui instance;
    private GameGui(){

    }
    public static GameGui getGameGui(){
        if(instance==null) instance=new GameGui();
        return instance;
    }

    @Override
    public void visit(EndGame event) {

    }

    @Override
    public void visit(StartGame event) {

    }

    @Override
    public void visit(JoinGame event) {

    }

    @Override
    public void visit(StartPlayerTurn event) {

    }

    @Override
    public void visit(WaitYourTurn event) {

    }

    @Override
    public void visit(ShowAllCards event) {

    }

    @Override
    public void visit(SelectCellOfWindowView event) {

    }

    @Override
    public void visit(SelectDiceFromDraftpool event) {

    }

    @Override
    public void visit(SelectToolCard event) {

    }

    @Override
    public void visit(ShowErrorMessage event) {

    }

    @Override
    public void visit(UpdateAllToolCard event) {

    }

    @Override
    public void visit(UpdateSingleToolCardCost event) {

    }

    @Override
    public void visit(UpdateDicePool event) {

    }

    @Override
    public void visit(InitialWindowPatternCard event) {

    }

    @Override
    public void visit(UpdateSinglePlayerHand event) {

    }

    @Override
    public void visit(UpdateAllPublicObject event) {

    }

    @Override
    public void visit(UpdateSingleCell event) {

    }

    @Override
    public void visit(UpdateSinglePlayerTokenAndPoints event) {

    }

    @Override
    public void visit(UpdateSinglePrivateObject event) {

    }

    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {

    }

    @Override
    public void visit(UpdateSingleWindow event) {

    }

    @Override
    public void visit(UpdateInitialWindowPatternCard event) {

    }

    @Override
    public void visit(UpdateInitDimRound event) {

    }

    @Override
    public void showMessage(EventView eventView) {
        eventView.accept(this);
    }
}
