package it.polimi.se2018.view.cli;

import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventViewFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CLI Event handler.
 *
 * @author Matteo Formentin
 */
public class CliController implements UIInterface, ViewVisitor, ViewControllerVisitor, ViewModelVisitor {

    private CliMessage cliMessage;
    private CliParserNonBlocking cliParser;
    private ClientController client;

    private int currentRound;
    private int currentTurn;
    private DiceStack[] roundTrack;
    private ToolCard[] toolCard;
    private DiceStack dicePool;
    private ObjectivePublicCard[] objectivePublicCards;
    // need for 1 time
    private WindowPatternCard[] windowPatternCardsToChoose;

    //the players
    private String[] playersName;
    private WindowPatternCard[] windowPatternCardOfEachPlayer;
    private DiceStack[] handOfEachPlayer;
    private int[] favorTokenOfEachPlayer;
    private int[] pointsOfEachPlayer;
    private ObjectivePrivateCard[] objectivePrivateCardOfEachPlayers;//almost all null until the end game
    private int playerId;

    private Thread currentTask;
    private AtomicBoolean isInputActive;


    public CliController(ClientController clientController) {
        client = clientController;
        cliMessage = new CliMessage();
        isInputActive = new AtomicBoolean(true);
        cliParser = new CliParserNonBlocking(isInputActive);
        cliMessage.splashScreen();
        cliParser.readSplash();
        initConnection();
        login();
    }

    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************

    public void showMessage(EventView eventView) {
        eventView.acceptGeneric(this);
    }

    @Override
    public void visit(EventViewFromController event) {
        Runnable exec = () -> {
            Thread.currentThread().setName("Visitor Handler: " + event.getClass().getSimpleName());
            event.acceptControllerEvent(this);
        };
        currentTask = new Thread(exec);
        currentTask.start();
    }

    @Override
    public void visit(EventViewFromModel event) {
        Runnable exec = () -> {
            Thread.currentThread().setName("Visitor Handler");
            event.acceptModelEvent(this);
        };
        new Thread(exec).start();
    }

    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************


    @Override
    public void visit(StartGame event) {
        playerId = event.getPlayerId();
        playersName = event.getPlayersName();
        windowPatternCardOfEachPlayer = new WindowPatternCard[playersName.length];
        objectivePrivateCardOfEachPlayers = new ObjectivePrivateCard[playersName.length];
        handOfEachPlayer = new DiceStack[playersName.length];
        favorTokenOfEachPlayer = new int[playersName.length];
        pointsOfEachPlayer = new int[playersName.length];
        cliMessage.showGameStarted(playersName);
    }

    @Override
    public void visit(ShowAllCards event) {
        cliMessage.showObjectivePublicCardMessage();
        for (ObjectivePublicCard card : objectivePublicCards) {
            cliMessage.showObjectivePublicCard(card);
        }
        cliMessage.println();

        cliMessage.showObjectivePrivateCardMessage();
        cliMessage.showObjectivePrivateCard(objectivePrivateCardOfEachPlayers[playerId]);
        cliMessage.println();

        cliMessage.showToolCardMessage();
        for (ToolCard card : toolCard) {
            cliMessage.showToolCard(card);
        }

        cliMessage.println();
    }

    @Override
    public void visit(MoveTimeoutExpired event) {
        isInputActive.set(false);
        cliMessage.showMoveTimeoutExpired();
        //if(currentTask!=null) currentTask.stop();
    }

    @Override
    public void visit(SelectInitialWindowPatternCard event) {
        isInputActive.set(true);
        cliMessage.showYourTurnScreen();
        cliMessage.showMessage("Quando sei pronto per scegliere la carta");
        int read = cliParser.readSplash();
        if (read != -1) {
            for (WindowPatternCard card : windowPatternCardsToChoose) {
                cliMessage.showWindowPatternCard(card);
            }

            cliMessage.showInitialWindowPatternCardSelection();
            int selection = cliParser.parseInt(3);
            if (selection != -1) {
                cliMessage.eraseScreen();
                EventController packet = new ControllerSelectInitialWindowPatternCard();
                packet.setPlayerId(playerId);
                ((ControllerSelectInitialWindowPatternCard) packet).setSelectedIndex(selection);
                client.sendEventToController(packet);
            }
        }
    }

    @Override
    public void visit(InitialEnded event) {

    }

    @Override
    public void visit(WaitYourTurn event) {
        cliMessage.showWaitYourTurnScreen(playersName[event.getIndexCurrentPlayer()]);
    }

    /**
     * Method to handle the start of the turn
     * <p>
     * maybe should receive and process some attribute for able/disable some options
     * for example: when you place one die the next event from the controller
     * set the event's boolean insert die to false and the menu won't show this option
     *
     * @param event of type startPlayer
     */
    @Override
    public void visit(StartPlayerTurn event) {
        isInputActive.set(true);
        turn();
    }

    @Override
    public void visit(EndGame event) {
        //TODO when the player received the update show the point of all the players
        //it's cool too if we can make the point accumulated by each Player a class with 7 different fields:
        // private object, the 3 public object, the remain favor token, the lost points and the total of all

    }

    @Override
    public void visit(JoinGame event) {
        //TODO
    }

    /**
     * Receive the Event for select which dice take from the draft pool and send the packet to the controller(throught)
     *
     * @param event
     */
    @Override
    public void visit(SelectDiceFromDraftPool event) {
        cliMessage.showDicePool(dicePool);
        int diceIndex = cliParser.parseInt();
        if (diceIndex != -1) {
            sendInfo(diceIndex);
        }
    }

    @Override
    public void visit(SelectCellOfWindow event) {// meglio inserire da 1 a 5 più comprensibile
        cliMessage.showChoiceRow();
        int row = cliParser.parseInt();
        cliMessage.showChoiceColumn();
        int column = cliParser.parseInt();

        if (row != -1 && column != -1) {
            sendInfo(row-1,column-1);
        }
    }
    @Override
    public void visit(SelectDiceFromRoundTrack event) {
        cliMessage.showChoiceRound();
        int round = cliParser.parseInt();
        cliMessage.showChoiceInRound();
        int index = cliParser.parseInt();

        if (round != -1 && index != -1) {
            sendInfo(round-1,index);
        }
    }

    @Override
    public void visit(SelectValueDice event) {
        cliMessage.showValueDice();
        int value = cliParser.parseInt();
        if (value != -1 ) {
            sendInfo(value);
        }
    }

    @Override
    public void visit(SelectIncrementOrDecreaseDice event) {
        cliMessage.showIncrementDecrement();
        int value = cliParser.parseInt();

        if (value != -1 ) {
            sendInfo(value);
        }
    }

    public void sendInfo(int info){
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] infoPacket = new int[1];
        infoPacket[0] = info;
        packet.setInfo(infoPacket);
        client.sendEventToController(packet);
    }
    public void sendInfo(int info1, int info2){
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] infoPacket = new int[2];
        infoPacket[0] = info1;
        infoPacket[1] = info2;
        packet.setInfo(infoPacket);
        client.sendEventToController(packet);
    }

    @Override
    public void visit(SelectToolCard event) {
        cliMessage.eraseScreen();
        cliMessage.showToolCardMessage();
        for (ToolCard card : toolCard) {
            cliMessage.showToolCard(card);
            cliMessage.println();
        }
        cliMessage.showToolCardChoice(toolCard);
        int indexTooLCard = cliParser.parsePositiveInt(toolCard.length) - 1;
        if (indexTooLCard != -1) {
            ControllerSelectToolCard packet = new ControllerSelectToolCard();
            packet.setPlayerId(playerId);
            packet.setIndexToolCard(indexTooLCard);
            client.sendEventToController(packet);
        }
    }

    @Override
    public void visit(MessageError event) {
        cliMessage.showMessage(event.getMessage());
        cliParser.readSplash();
        if (event.isShowMenuTurn()) turn();
    }

    @Override
    public void visit(MessageOk event) {
        cliMessage.showGreenMessage(event.getMessageConfirm());
        if (event.isShowTurnMenu()) turn();
    }

    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************

    @Override
    public void visit(UpdateInfoCurrentTurn event) {
        currentRound=event.getCurrentRound();
        currentTurn=event.getCurrentTurn();
    }
    @Override
    public void visit(UpdateInitialWindowPatternCard event) {
        windowPatternCardsToChoose = event.getInitialWindowPatternCard();
    }
    @Override
    public void visit(UpdateAllToolCard event) {
        toolCard = event.getToolCard();
    }
    @Override
    public void visit(UpdateAllPublicObject event) {
        objectivePublicCards = event.getPublicCards();
    }
    @Override
    public void visit(UpdateInitDimRound event) {
        roundTrack = event.getRoundTrack();
    }
    @Override
    public void visit(UpdateSingleToolCardCost event) {
        toolCard[event.getIndexToolCard()].setFavorToken(event.getCostToolCard());
    }
    @Override
    public void visit(UpdateDicePool event) {
        dicePool = event.getDicePool();
    }


    @Override
    public void visit(UpdateSinglePlayerHand event) {
        handOfEachPlayer[event.getIndexPlayer()] = event.getHandPlayer();
    }
    @Override
    public void visit(UpdateSingleCell event) {
        windowPatternCardOfEachPlayer[event.getIndexPlayer()].getCell(event.getLine(), event.getColumn()).setDice(event.getDice());
        if (event.getIndexPlayer() != playerId) {
            cliMessage.showOpponentInsertDice(playersName[event.getIndexPlayer()], event.getLine(), event.getColumn());
            cliMessage.showWindowPatternCard(windowPatternCardOfEachPlayer[event.getIndexPlayer()]);
        }
    }
    @Override
    public void visit(UpdateSinglePlayerTokenAndPoints event) {
        favorTokenOfEachPlayer[event.getIndexInGame()] = event.getFavorToken();
        pointsOfEachPlayer[event.getIndexInGame()] = event.getPoints();
    }
    @Override
    public void visit(UpdateSinglePrivateObject event) {
        objectivePrivateCardOfEachPlayers[event.getIndexPlayer()] = event.getPrivateCard();
    }
    @Override
    public void visit(UpdateSingleTurnRoundTrack event) {
        roundTrack[event.getIndexRound()] = event.getRoundDice();
    }

    @Override
    public void visit(UpdateSingleWindow event) {
        windowPatternCardOfEachPlayer[event.getIndexPlayer()] = event.getWindowPatternCard();
    }


    //OPERATION HANDLER
    private void initConnection() {
        boolean flag = false;
        do {
            int socketRmi = 0;
            String ip;
            cliMessage.showIpRequest();
            ip = cliParser.parseIp();

            cliMessage.showSocketRmi();
            cliParser.parseInt(1);

            try {

                client.startClient(ip, socketRmi);
                flag = true;
                cliMessage.showConnectionSuccessful();
                cliMessage.println();
            } catch (Exception ex) {
                cliMessage.showMessage(ex.getMessage());
            }

        } while (!flag);
    }

    private void login() {
        boolean flag = false;
        String name = "";
        while (!flag) {
            cliMessage.showInsertNickname();
            name = cliParser.parseNickname();
            if (client.login(name)) {
                flag = true;
            } else {
                cliMessage.showNicknameExists();
            }
        }
        cliMessage.showWelcomeNickname(name);
    }

    private void turn() {
        cliMessage.eraseScreen();
        cliMessage.showYourTurnScreen();
        cliMessage.showRoundAndTurn(currentRound,currentTurn);
        cliMessage.showWindowPatternCard(getMyWindowPatternCard());
        cliMessage.showHandPlayer(getMyHand());
        cliMessage.showMainMenu();
        int option = cliParser.parsePositiveInt(7);

        if (option != -1) {
            switch (option) {
                //insert a dice in the window
                case 1:
                    EventController packetInsertDice = new ControllerMoveDrawAndPlaceDie();
                    packetInsertDice.setPlayerId(playerId);
                    client.sendEventToController(packetInsertDice);
                    break;
                //Use tool card
                case 2:
                    EventController packetUseTool = new ControllerMoveUseToolCard();
                    packetUseTool.setPlayerId(playerId);
                    client.sendEventToController(packetUseTool);
                    break;

                //End turn
                case 3:
                    EventController packetEnd = new ControllerEndTurn();
                    packetEnd.setPlayerId(playerId);
                    client.sendEventToController(packetEnd);
                    break;

                //Show private object
                case 4:
                    cliMessage.eraseScreen();
                    cliMessage.showObjectivePrivateCardMessage();
                    cliMessage.showObjectivePrivateCard(objectivePrivateCardOfEachPlayers[playerId]);
                    cliParser.readSplash();
                    turn();
                    break;

                //Show public object
                case 5:
                    cliMessage.eraseScreen();
                    cliMessage.showObjectivePublicCardMessage();
                    for (ObjectivePublicCard card : objectivePublicCards) {
                        cliMessage.showObjectivePublicCard(card);
                        cliMessage.println();
                    }
                    cliParser.readSplash();
                    turn();
                    break;

                //Show opponents window pattern card
                case 6:
                    cliMessage.eraseScreen();
                    cliMessage.showOpponentWindowMessage();
                    for (int i = 0; i < windowPatternCardOfEachPlayer.length; i++) {
                        if (i == playerId) continue;
                        cliMessage.showOpponentWindow(playersName[i]);
                        cliMessage.showWindowPatternCard(windowPatternCardOfEachPlayer[i]);
                        cliMessage.println();
                    }
                    turn();
                    break;

                //Show dice pool
                case 7:
                    cliMessage.showDiceStack(dicePool);
                    cliParser.readSplash();
                    turn();
                    break;

                //Show round track
                case 8:
                    //TODO tracciato round
                    turn();
                    break;
            }
        }
    }

    private WindowPatternCard getMyWindowPatternCard() {
        return windowPatternCardOfEachPlayer[playerId];
    }

    private DiceStack getMyHand() {
        return handOfEachPlayer[playerId];
    }

    private String getMyName() {
        return playersName[playerId];
    }

    @Override
    public void visit(UpdateStatPodium event) {
        for(int i=0; i<event.getSortedPlayer().length;i++){
            System.out.println();
            System.out.print( (i+1)+"° Posto: "+playersName[event.getOneSortedPlayerInfo(i,0)]);
            for(int j=1; j<event.getOneSortedPlayer(i).length;j++){
                System.out.print(" | " +event.getDescription(j)+": "+event.getOneSortedPlayerInfo(i,j));
            }
            System.out.println();
        }
    }
}
