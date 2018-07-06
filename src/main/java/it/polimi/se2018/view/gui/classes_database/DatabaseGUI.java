package it.polimi.se2018.view.gui.classes_database;

public class DatabaseGUI { //implements ViewModelVisitor {
/*
    private WaitGame waitGame;
    private GuiGame guiGame;
   // private ShowCardBox showCardBox;





    private int currentRound;
    private int currentTurn;
    private DiceStack[] roundTrack;
    private ToolCard[] toolCard;
    private DiceStack dicePool;
    private ObjectivePublicCard[] objectivePublicCards;
    // need for 1 time
    private WindowPatternCard windowPatternCardsToChoose;

    //the players
    private String[] playersName;
    boolean[] connected;
    private WindowPatternCard[] windowPatternCardOfEachPlayer;
    private DiceStack[] handOfEachPlayer;
    private int[] favorTokenOfEachPlayer;
    private int[] pointsOfEachPlayer;
    private ObjectivePrivateCard[] objectivePrivateCardOfEachPlayers; //almost all null until the end game
    private int playerId;

    //Updated stat

    private int[][] ranking;
    private int currentRound;
    private int currentTurn;
    private DiceStack[] roundTrack;
    private ToolCard[] toolCard;
    private DiceStack dicePool;
    private ObjectivePublicCard[] objectivePublicCards;
    // need for 1 time
    private WindowPatternCard[] windowPatternCardsToChoose;

    //the players
    private LinkedList<PlayerOnline> players;
    private WindowPatternCard[] windowPatternCardOfEachPlayer;
    private DiceStack[] handOfEachPlayer;
    private int[] favorTokenOfEachPlayer;
    private int[] pointsOfEachPlayer;
    private ObjectivePrivateCard[] objectivePrivateCardOfEachPlayers; //almost all null until the end game
    private int playerId;

    //Updated stat

    private int[][] ranking;

    //*************************************************************************************
    //*************************************************************************************
    //                           Visitor Event From Model
    //*************************************************************************************
    //*************************************************************************************
    @Override
    public void visit(UpdateNamePlayers event) {

    }

    @Override
    public void visit(UpdateInitDimRound event) {

    }

    @Override
    public void visit(UpdateAllToolCard event) {

    }

    @Override
    public void visit(UpdateAllPublicObject event) {

    }

    @Override
    public void visit(UpdateSinglePrivateObject event) {

    }

    @Override
    public void visit(UpdateInitialWindowPatternCard event) {
        windowPatternCardsToChoose = event.getInitialWindowPatternCard();
    }

    @Override
    public void visit(UpdateSingleWindow event) {

    }

    @Override
    public void visit(UpdateSinglePlayerHand event) {

    }

    @Override
    public void visit(UpdateSingleCell event) {

    }

    @Override
    public void visit(UpdateDicePool event) {

    }

    /**
     * method for update the current round and turn
     *
     * @param event event received for the server
     */
 /*   @Override
    public void visit(UpdateInfoCurrentTurn event) {
        currentRound = event.getCurrentRound();
        currentTurn = event.getCurrentTurn();
    }

    @Override
    public void visit(UpdateSinglePlayerToken event) {

    }

    @Override
    public void visit(UpdateSingleToolCardCost event) {

    }

    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {

    }

    @Override
    public void visit(UpdateCurrentPoint event) {

    }

    @Override
    public void visit(UpdateStatPodium event) {

    }


    @Override
    public void visit(UpdateDisconnectionDuringSetup event) {

    }

    @Override
    public void visit(UpdateNamePlayersDuringSetUp event) {
        ObservableList<PlayerOnline> allPlayerOnline= waitGame.getPlayerOnlineSingleton();
        for (int i=0;i<event.getPlayerNames().length;i++) {
            if(x.getNickname().equals(event.getPlayerNames())){
                System.out.println("trovato il player disconnesso");
                waitGame.deletePlayerKicked(event.getPlayerNames());
            }
        }
    }

    /**
     * update the data in case the player make a disconnection
     *
     * @param event received by the server
     */
  /*  @Override
    public void visit(UpdateDisconnectionDuringGame event) {

    }

    @Override
    public void visit(UpdatePlayerConnection event) {

    }*/
}
