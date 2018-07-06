package it.polimi.se2018.view.cli;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.client.ClientFactory;
import it.polimi.se2018.exception.network_exception.NoPortRightException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.*;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller.*;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.ViewVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.ViewControllerVisitor;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.ConnectionDown;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.LoginResponse;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;
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

    private static CliController instance;
    private static ClientFactory factory;
    private CliMessage cliMessage;
    private CliParserNonBlocking cliParser;
    private CliParser cliParserBlocking;


    private ClientController client;
    //Server alternativo
    private AbstractClient2 client2;
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
    boolean[] connected;
    private WindowPatternCard[] windowPatternCardOfEachPlayer;
    private DiceStack[] handOfEachPlayer;
    private int[] favorTokenOfEachPlayer;
    private int[] pointsOfEachPlayer;
    private ObjectivePrivateCard[] objectivePrivateCardOfEachPlayers; //almost all null until the end game
    private int playerId;

    //Updated stat

    private int[][] ranking;

    private Thread currentTask;
    private AtomicBoolean isInputActive;
    private Object MUTEX;

    public ClientFactory getFactory() {
        return factory;
    }

    /**
     * CliController constructor
     */
    public CliController(ClientController clientController) {
        client = clientController;
        cliMessage = new CliMessage();
        isInputActive = new AtomicBoolean(true);
        cliParser = new CliParserNonBlocking(isInputActive);
        MUTEX = new Object();
        cliMessage.splashScreen();
        cliParser.readSplash();
        initConnection();
        login();
    }

    /**
     * CliController constructor
     */
    public CliController() {
        cliMessage = new CliMessage();
        isInputActive = new AtomicBoolean(true);
        cliParser = new CliParserNonBlocking(isInputActive);
        MUTEX = new Object();
        factory = ClientFactory.getClientFactory();
        cliMessage.splashScreen();
        cliParser.readSplash();
    }

    /**
     * Constructor for the socket Cli
     *
     * @param client2
     */
    public CliController(AbstractClient2 client2) {
        this.client2 = client2;
        cliMessage = new CliMessage();
        isInputActive = new AtomicBoolean(true);
        cliParser = new CliParserNonBlocking(isInputActive);
        MUTEX = new Object();
        client2.connectToServer2();
     /*   System.out.println("Inserisci il nome: ");
        LoginRequest packet = new LoginRequest(cliParser.parseNickname());
        sendEventToNetwork(packet);*/
        System.out.println("uscito dal costruttore");
    }

    public boolean checkShutDown() {
        System.out.println("Digita 0 per Resettare la connessione, 1 per uscire");
        return (cliParserBlocking.parseInt(1) == 1);
    }

    /**
     * Entry point for client
     */
    public static void main(String[] args) {
        instance = new CliController();
        System.out.println("uscito dal constructor della cli");
        instance.initConnection();
       // instance.login();
        System.out.println("uscito dal main");

    }

    /**
     * Start cli
     */
    public void start() {
        cliMessage.splashScreen();
        cliParser.readSplash();
    }

    /**
     * Print one error message
     */
    public void errPrintln(String error) {
        System.err.println();
        System.err.println(error);
        System.err.println();
    }

    @Override
    public void loginOk() {

    }

    /**
     * Send one event to server
     */
    public void sendEventToNetwork(EventController packet) {
        if (factory == null) client.sendEventToController(packet);
        else {
            client2.sendEventToController2(packet);
        }
    }

    public void sendEventToNetwork(EventServer packet) {
        if (factory == null) ;// client.sendEventToController(packet);
        else {
            client2.sendEventToController2(packet);
        }
    }

    @Override
    public void restartConnection(String message) {

    }

    /**
     * Connect with server
     */
    private void initConnection() {
        boolean flag = false;
        do {
            int socketRmi;
            int port = 0;
            String ip;
            cliMessage.showIpRequest();
            ip = cliParser.parseIp();

            cliMessage.showSocketRmi();
            socketRmi = cliParser.parseInt(1);
            if (flag) {
                cliMessage.showPortRequest();
                port = cliParser.parseInt(1);
            }
            try {
                if (factory == null) client.startClient(ip, socketRmi);
                else {
                    client2 = factory.createClient(this, ip, port, socketRmi, true);
                    client2.connectToServer2();
                }
                flag = true;
                cliMessage.showConnectionSuccessful();
                cliMessage.println();
            } catch (ConnectionProblemException ex) {
                ex.printStackTrace();
                cliMessage.showMessage(ex.getMessage());
                ex.printStackTrace();
            } catch (NoPortRightException ex) {
                ex.printStackTrace();
                cliMessage.showMessage(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                cliMessage.showMessage(ex.getMessage());
            }
        } while (!flag);
    }

    /**
     * EventPreGame to server
     */
    private void login() {
        boolean flag = false;
        String name = "";
        while (!flag) {
            cliMessage.showInsertNickname();
            name = cliParser.parseNickname();
            if (factory == null) {
                if (client.login(name)) {
                    flag = true;
                } else {
                    cliMessage.showNicknameExists();
                }
            } else {
                //TODO creare
                EventPreGame packet = new LoginRequest(cliParser.parseNickname());
                sendEventToNetwork(packet);

            }

        }
        cliMessage.showWelcomeNickname(name);
    }

    /**
     * Show turn menu
     */
    private void turn() {
        cliMessage.eraseScreen();
        cliMessage.showYourTurnScreen();
        cliMessage.showRoundAndTurn(currentRound, currentTurn);
        cliMessage.showWindowPatternCard(getMyWindowPatternCard());
        cliMessage.showHandPlayer(getMyHand());
        cliMessage.showMainMenu();
        int option = cliParser.parsePositiveInt(8);

        if (option != -1) {
            switch (option) {
                //insert a dice in the window
                case 1:
                    EventController packetInsertDice = new ControllerMoveDrawAndPlaceDie();
                    packetInsertDice.setPlayerId(playerId);
                    sendEventToNetwork(packetInsertDice);
                    break;
                //Use tool card
                case 2:
                    EventController packetUseTool = new ControllerMoveUseToolCard();
                    packetUseTool.setPlayerId(playerId);
                    sendEventToNetwork(packetUseTool);
                    break;

                //End turn
                case 3:
                    EventController packetEnd = new ControllerEndTurn();
                    packetEnd.setPlayerId(playerId);
                    sendEventToNetwork(packetEnd);
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
                    cliParser.readSplash();
                    turn();
                    break;

                //Show dice pool
                case 7:
                    cliMessage.eraseScreen();
                    cliMessage.showDiceStack(dicePool);
                    cliParser.readSplash();
                    turn();
                    break;

                //Show round track
                case 8:
                    cliMessage.eraseScreen();
                    cliMessage.showRoundTrack(roundTrack);
                    cliParser.readSplash();
                    turn();
                    break;
            }
        }
    }

    /**
     * Get my window from player window array
     */
    private WindowPatternCard getMyWindowPatternCard() {
        return windowPatternCardOfEachPlayer[playerId];
    }

    /**
     * Get my hand from player hand array
     */
    private DiceStack getMyHand() {
        return handOfEachPlayer[playerId];
    }

    /**
     * Get my name from player name array
     */
    private String getMyName() {
        return playersName[playerId];
    }


    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************

    /**
     * Handler for visitor pattern
     */
    public void showEventView(EventClient eventClient) {
        eventClient.acceptGeneric(this);
    }

    @Override
    public void visit(EventClientFromController event) {
        synchronized (MUTEX) {
            Runnable exec = () -> {
                Thread.currentThread().setName("Visitor Handler: " + event.getClass().getSimpleName());
                event.acceptControllerEvent(this);
            };
            currentTask = new Thread(exec);
            currentTask.start();
        }
    }

    @Override
    public void visit(EventClientFromModel event) {
        synchronized (MUTEX) {
            event.acceptModelEvent(this);
        }
         /*Runnable exec = () -> {
            Thread.currentThread().setName("Visitor Handler");
            event.acceptModelEvent(this);
        };
        new Thread(exec).start();*/

    }

    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************


    @Override
    public void visit(LoginResponse event) {
        if (event.isLoginSuccessFull()) System.out.println("Hai effetturato il login");
        else {
            System.out.println(event.getCause());
            System.out.println("0 per riprovare il login 1 per spegnere");
            if (cliParser.parseInt(1) == 0) {
                AskLogin packet = new AskLogin();
                visit(packet);
            } else {
                client2.shutDownClient2();
            }
        }
    }

    @Override
    public void visit(ConnectionDown event) {
        //TODO messaggio di connessione down
        System.out.println("Connessione Caduta perchè uscito dal contructor della cli");
    }

    @Override
    public void visit(AskLogin event) {
        //TODO da sistemare
        System.out.println("Inserisci il nome: ");
        LoginRequest packet = new LoginRequest(cliParser.parseNickname());
        sendEventToNetwork(packet);
    }

    @Override
    public void visit(StartGame event) {
        cliMessage.showGameStarted(event.getPlayersName());
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
                sendEventToNetwork(packet);
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
        cliMessage.showEndGameScreen(ranking, playersName, playerId);
        System.exit(0);
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
            sendInfo(row - 1, column - 1);
        }
    }

    @Override
    public void visit(SelectDiceFromRoundTrack event) {
        cliMessage.showRoundTrack(roundTrack);
        cliMessage.showChoiceRound();
        int round = cliParser.parseInt(currentRound + 1);
        cliMessage.showChoiceInRound();
        int index = cliParser.parseInt(roundTrack[round].size());

        if (round != -1 && index != -1) {
            sendInfo(round - 1, index);
        }
    }

    @Override
    public void visit(SelectValueDice event) {
        cliMessage.showValueDice();
        int value = cliParser.parseInt();
        if (value != -1) {
            sendInfo(value);
        }
    }

    @Override
    public void visit(SelectIncrementOrDecreaseDice event) {
        cliMessage.showIncrementDecrement();
        int value = cliParser.parseInt();

        if (value != -1) {
            sendInfo(value);
        }
    }

    public void sendInfo(int info) {
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] infoPacket = new int[1];
        infoPacket[0] = info;
        packet.setInfo(infoPacket);
        sendEventToNetwork(packet);
    }

    public void sendInfo(int info1, int info2) {
        ControllerInfoEffect packet = new ControllerInfoEffect();
        packet.setPlayerId(playerId);
        int[] infoPacket = new int[2];
        infoPacket[0] = info1;
        infoPacket[1] = info2;
        packet.setInfo(infoPacket);
        sendEventToNetwork(packet);
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
            sendEventToNetwork(packet);
        }
    }

    @Override
    public void visit(MessageError event) {
        cliMessage.showMessage(event.getMessage());
        // cliParser.readSplash();
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
        currentRound = event.getCurrentRound();
        currentTurn = event.getCurrentTurn();
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
    public void visit(UpdateNamePlayers event) {
        //TODO controllare che i nomi vengano aggiornati
        playerId = event.getPlayerId();
        playersName = event.getPlayerNames();
        windowPatternCardOfEachPlayer = new WindowPatternCard[playersName.length];
        objectivePrivateCardOfEachPlayers = new ObjectivePrivateCard[playersName.length];
        handOfEachPlayer = new DiceStack[playersName.length];
        favorTokenOfEachPlayer = new int[playersName.length];
        pointsOfEachPlayer = new int[playersName.length];
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
    public void visit(UpdateSinglePlayerToken event) {
        favorTokenOfEachPlayer[event.getIndexInGame()] = event.getFavorToken();
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

    @Override
    public void visit(UpdateStatPodium event) {
        ranking = event.getSortedPlayer();
    }

    @Override
    public void visit(UpdateDisconnectionDuringSetup event) {
        cliMessage.showMessage("Il giocatore " + event.getName() + " ha lasciato la stanza");
    }

    @Override
    public void visit(UpdateNamePlayersDuringSetUp event) {
        playersName = event.getPlayerNames();
        boolean[] connected = new boolean[playersName.length];
        System.out.println("Giocatori attualmente nella stanza");
        System.out.println();
        for (int i = 0; i < playersName.length; i++) System.out.println("Nome: " + playersName[i]);
    }

    @Override
    public void visit(UpdateDisconnectionDuringGame event) {
        cliMessage.showMessage("Il giocatore " + event.getName() + " ha abbandonato la partita");
    }

    @Override
    public void visit(UpdatePlayerConnection event) {
        cliMessage.showGreenMessage("Il giocatore " + event.getName() + " si è connesso dalla partita.");
    }

    @Override
    public void visit(UpdateCurrentPoint event) {
        System.out.println("arrivato current Point");
        //TODO add the info of the current points (event during the game only for this player)
        //TODO evento che arriva ogni alla fine di ogni turno.
    }
}
