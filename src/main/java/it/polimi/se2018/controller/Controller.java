package it.polimi.se2018.controller;

import it.polimi.se2018.alternative_network.newserver.room.GameRoom;
import it.polimi.se2018.controller.effect.DicePoolEffect;
import it.polimi.se2018.controller.effect.EffectGame;
import it.polimi.se2018.controller.effect.EndTurn;
import it.polimi.se2018.controller.effect.InsertDice;
import it.polimi.se2018.exception.gameboard_exception.CurrentPlayerException;
import it.polimi.se2018.exception.gameboard_exception.GameIsOverException;
import it.polimi.se2018.exception.gameboard_exception.WindowPatternAlreadyTakenException;
import it.polimi.se2018.exception.gameboard_exception.WindowSettingCompleteException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyPlaceANewDiceException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.AlreadyUseToolCardException;
import it.polimi.se2018.exception.gameboard_exception.player_state_exception.PlayerException;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.*;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.event_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.EventClientFromController;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectCellOfWindow;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectInitialWindowPatternCard;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.request_input.SelectToolCard;
import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.UpdateRequestedByServer;
import it.polimi.se2018.model.UpdaterView;
import it.polimi.se2018.network.server.ServerController;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Class that implements the {@code ControllerVisitor} for execute the event that the view produced
 *
 * @author Matteo Formentin
 * @author Luca Genoni
 */

public class Controller implements ControllerVisitor, TimerCallback {
    private GameBoard gameBoard;
    private int playerNumber;
    private boolean restoreAble;
    //Server in cui si setterà la partita
    private ServerController server;
    private GameRoom gameRoom;


    //Lista di effetti da eseguire uno alla volta e spostati nello store una volta terminati
    private LinkedList<EffectGame> effectToRead;
    private int currentEffect;
    //Lista di effetti andati a buon fine e memorizzati. qui avviene il ripescagglio degli undo che rimette
    private LinkedList<EffectGame> effectGamesStored;

    long PLAYER_TIMEOUT; //ms!!
    private TimerThread playerTimeout;
    private UpdaterView updaterView;

    private boolean started;
    //   private boolean timer;

    /**
     * Controller constructor.
     *
     * @param server server on when the game is on.
     */
    public Controller(ServerController server, String[] playerName, GameRoom room) {
        //set up actual game
        this.server = server;
        this.gameRoom = room;
        this.playerNumber = playerName.length;
        gameBoard = new GameBoard(playerName);
        updaterView = new UpdaterView(gameBoard, server, room);
        //set up utils for the game
        restoreAble = false;
        try {
            Properties configProperties = new Properties();

            String timeConfig = "src/resources/configurations/gameroom_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(timeConfig);

            configProperties.load(inputConnection);
            // SERVER ADDRESS LOAD
            PLAYER_TIMEOUT = Integer.parseInt(configProperties.getProperty("turnTimeout")) * 1000;
        } catch (Exception e) {
            PLAYER_TIMEOUT = 90 * 1000;
            System.out.println("Errore caricamento");
        }
        playerTimeout = new TimerThread(this, PLAYER_TIMEOUT);
        //  timer = false;
        //List for effect
        effectToRead = new LinkedList<>();
        currentEffect = 0;
        effectGamesStored = new LinkedList<>();
        started = false;
        System.out.println("CONTROLLER CREATED!!!!!!!!!!!");
    }


    public UpdateRequestedByServer getUpdater() {
        return updaterView;
    }

    public void startController() {
        gameBoard.startGame(updaterView);
        sendInitCommand();
    }

    public void endGame() {
        gameBoard.setStopGame(true);
        playerTimeout.shutdown();
        for (int i = 0; i < playerNumber; i++) {
            EndGame endGame = new EndGame();
            endGame.setPlayerId(i);
            sendEventToView(endGame);
        }
        if(gameRoom!=null) gameRoom.endGame();
    }

    public void playerDown(int index) {
        if (started) {
            ControllerEndTurn event = new ControllerEndTurn();
            event.setIdGame(index);
            visit(event);
        }
    }

    public void winBecauseOfDisconnection(int winnerId) {
        //TODO vittoria a tavolino per mancanza di player
    }

    /**
     * Used when one player
     * made reLogin
     */
    public void playerUp(int index) {
        updaterView.updateInfoReLogin(index);
        String[] names= new String[gameBoard.getPlayer().length];
        for (int i = 0; i < gameBoard.getPlayer().length; i++) names[i]=gameBoard.getPlayer(i).getNickname();
        StartGame packet = new StartGame(names);
        packet.setPlayerId(index);
        sendEventToView(packet);
        if (started) {
            EventClientFromController packet2 = new SelectInitialWindowPatternCard();
            packet.setPlayerId(index);
            sendEventToView(packet);
        }
    }


    /**
     * method for se
     */
    public void sendInitCommand() {
        updaterView.updateInfoStart();
        //TODO forse questo mi permette di avviare il tutto correttamente
        for (int i = 0; i < playerNumber; i++) {
            //gameBoard.notifyAllCards(i);

        }
        for (int j = 0; j < playerNumber; j++) {
            ShowAllCards waitSetUp = new ShowAllCards();
            waitSetUp.setPlayerId(j);
            sendEventToView(waitSetUp);
        }
        //after showing the info ask the player to choose the window
        for (int i = 0; i < playerNumber; i++) {
            SelectInitialWindowPatternCard packet = new SelectInitialWindowPatternCard();
            packet.setPlayerId(i);
            sendEventToView(packet);
        }
        //TODO Start the timer per la selezione della window
        System.err.println("Iniziato il gioco, inviati tutti i pacchetti per l'inizio del game");
    }

    // EX UPDATE
    public void sendEventToController(EventController event) {
        if (!gameBoard.isStopGame()) event.accept(this);
    }

    //the handler respond with this method
    private void sendEventToView(EventClient event) {
        if (server == null) gameRoom.sendEventToView(event);
        else server.sendEventToView(event);
    }

    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player pick a window.
     *
     * @param event ControllerSelectInitialWindowPatternCard
     */
    @Override
    public void visit(ControllerSelectInitialWindowPatternCard event) {
        try {
            gameBoard.setWindowOfPlayer(event.getPlayerId(), event.getSelectedIndex());
            MessageOk packet = new MessageOk("Hai scelto la window Pattern.", false, true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (WindowSettingCompleteException ex) {
            started = true;
            //all the window are set
            for (int i = 0; i < playerNumber; i++) {
                EventClient packetFineInit = new InitialEnded();
                packetFineInit.setPlayerId(i);
                sendEventToView(packetFineInit);
            }
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            sendEventToView(turnPacket);
            //TODO fermare il timout per l'init e iniziare quello per il round.
            playerTimeout.shutdown();
            playerTimeout.startThread(gameBoard.getIndexCurrentPlayer());
        } catch (WindowPatternAlreadyTakenException ex) {
            //window already chosen
            MessageError packetError = new MessageError(ex.getMessage(), false, true);
            packetError.setPlayerId(event.getPlayerId());
            sendEventToView(packetError);
        } catch (Exception ex) {
            //strange exception retry the input
            MessageError packetError = new MessageError(ex.getMessage(), false, true);
            packetError.setPlayerId(event.getPlayerId());
            sendEventToView(packetError);
            SelectInitialWindowPatternCard packet = new SelectInitialWindowPatternCard();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode draw and place a die
     *
     * @param event ControllerSelectInitialWindowPatternCard
     */
    @Override
    public void visit(ControllerMoveDrawAndPlaceDie event) {
        EffectGame insertDice = new InsertDice(true, true, true, true);
        EffectGame drawDice = new DicePoolEffect(true);
        LinkedList<EffectGame> newList = new LinkedList<>();
        if (event.getPlayerId() == gameBoard.getIndexCurrentPlayer()) {
            if (gameBoard.getPlayer(event.getPlayerId()).isHasPlaceANewDice()) {
                showErrorMessage(new AlreadyPlaceANewDiceException(), event.getPlayerId(), true);
            } else {
                if (gameBoard.getPlayer(event.getPlayerId()).isHasDrawNewDice()) {
                    newList.addLast(insertDice);
                } else {
                    newList.addLast(drawDice);
                    newList.addLast(insertDice);
                }
                effectToRead = newList;
                EventClient packet = effectToRead.getFirst().eventViewToAsk();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }
        } else {
            showErrorMessage(new CurrentPlayerException(), event.getPlayerId(), false);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode play a tool card
     *
     * @param event ControllerMoveUseToolCard
     */
    @Override
    public void visit(ControllerMoveUseToolCard event) {
        if (event.getPlayerId() == gameBoard.getIndexCurrentPlayer()) {
            if (gameBoard.getPlayer(event.getPlayerId()).isHasUsedToolCard()) {
                showErrorMessage(new AlreadyUseToolCardException(), event.getPlayerId(), true);
            } else {
                SelectToolCard packet = new SelectToolCard();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }
        } else {
            showErrorMessage(new CurrentPlayerException(), event.getPlayerId(), false);
        }
    }

    @Override
    public synchronized void visit(ControllerEndTurn event) {
        try {
            if (restoreAble && !gameBoard.getPlayer(event.getPlayerId()).getHandDice().isEmpty())
                effectGamesStored.getLast().undo();
            EffectGame endTurn = new EndTurn(false);
            endTurn.doEffect(gameBoard, event.getPlayerId(), null);
            effectGamesStored.addLast(endTurn);
            effectToRead = new LinkedList<>();
            restoreAble = false;
            //send info
            sendWaitTurnToAllTheNonCurrent(gameBoard.getIndexCurrentPlayer());
            StartPlayerTurn turnPacket = new StartPlayerTurn();
            turnPacket.setPlayerId(gameBoard.getIndexCurrentPlayer());
            sendEventToView(turnPacket);
            //Restart timer
            playerTimeout.shutdown();
            playerTimeout.startThread(gameBoard.getIndexCurrentPlayer());
        } catch (GameIsOverException ex) {
            endGame();
            ex.printStackTrace();
        } catch (CurrentPlayerException ex) {
            System.out.println("Questa eccezione va catturata e non reinviata");
            ex.printStackTrace();
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(), false);
        }
    }

    /**
     * Method of the Visitor Pattern, event received from the view
     * to let the player initiate the mode play a tool card
     *
     * @param event ControllerSelectDiceFromDraftPool
     */
    @Override
    public void visit(ControllerSelectDiceFromDraftPool event) {
        try {
            gameBoard.addNewDiceToHandFromDicePool(event.getPlayerId(), event.getIndex());
            SelectCellOfWindow packet = new SelectCellOfWindow();
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (CurrentPlayerException ex) {
            showErrorMessage(ex, event.getPlayerId(), false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(), true);

        }
    }

    @Override
    public void visit(ControllerSelectCellOfWindow event) {
        try {
            gameBoard.insertDice(event.getPlayerId(), event.getLine(), event.getColumn(), true);
            EventClient packet = new MessageOk("la mossa si è conclusa con successo", true);
            packet.setPlayerId(event.getPlayerId());
            sendEventToView(packet);
        } catch (CurrentPlayerException ex) {
            showErrorMessage(ex, event.getPlayerId(), false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(), true);
        }
    }


    /**
     * method for select the toolcard
     *
     * @param event
     */
    public void visit(ControllerSelectToolCard event) {
        try {
            gameBoard.useToolCard(event.getPlayerId(), event.getIndexToolCard());
            restoreAble = gameBoard.getToolCard(event.getIndexToolCard()).isUndoAble();
            //load the effect of the tool card
            effectToRead = gameBoard.getToolCard(event.getIndexToolCard()).getCopyListEffect();
            effectToRead.addFirst(null);
            accessToEffect(event.getPlayerId(), null);
        } catch (CurrentPlayerException ex) {
            showErrorMessage(ex, event.getPlayerId(), false);
        } catch (Exception ex) {
            showErrorMessage(ex, event.getPlayerId(), true);
        }
    }

    /**
     * method for receive the parameter of some moves.
     *
     * @param event
     */
    @Override
    public void visit(ControllerInfoEffect event) {
        if (!effectToRead.isEmpty()) {// se ci sono effetti da leggere
            try {
                accessToEffect(event.getPlayerId(), event.getInfo());
            } catch (CurrentPlayerException ex) {
                showErrorMessage(ex, event.getPlayerId(), false);
            } catch (PlayerException ex) {
                showErrorMessage(ex, event.getPlayerId(), true);
            } catch (Exception ex) {
                showErrorMessage(ex, event.getPlayerId(), false);
                EventClient packet = effectToRead.getFirst().eventViewToAsk();
                packet.setPlayerId(event.getPlayerId());
                sendEventToView(packet);
            }
        }
    }

    private void accessToEffect(int idPlayer, int[] info) throws Exception {
        if (effectToRead.getFirst() != null) {
            effectToRead.getFirst().doEffect(gameBoard, idPlayer, info);
            effectGamesStored.addLast(effectToRead.getFirst());
            effectToRead.removeFirst();
        } else {
            effectToRead.removeFirst(); //first flow move
        }
        //lettura nuovo effetto
        if (effectToRead.isEmpty()) {
            EventClient packet = new MessageOk("il flusso della mossa si è concluso con successo", true);
            packet.setPlayerId(idPlayer);
            sendEventToView(packet);
            restoreAble = false;
        } else { //there is a another effect to read
            EventClient packet = effectToRead.getFirst().eventViewToAsk();
            if (packet == null) accessToEffect(idPlayer, null); //effect without the need of the player to act
            else { //effect with the need of the player input
                packet.setPlayerId(idPlayer);
                sendEventToView(packet);
            }
        }
    }


    private void showErrorMessage(Exception ex, int idPlayer, boolean showMenuTurn) {
        ex.printStackTrace();
        MessageError packet = new MessageError(ex.getMessage(), showMenuTurn);
        packet.setPlayerId(idPlayer);
        sendEventToView(packet);
    }


    private void sendWaitTurnToAllTheNonCurrent(int currentPlayerId) {
        for (int j = 0; j < playerNumber; j++) {
            if (j == currentPlayerId) continue;
            EventClient waitTurn = new WaitYourTurn(currentPlayerId);
            waitTurn.setPlayerId(j);
            sendEventToView(waitTurn);
        }
    }

    @Override
    public void timerCallback() {
        //TODO se si vuole avere il tempo anche per il setUP utilizzare questo callback
        throw new UnsupportedOperationException();
    }

    @Override
    public void timerCallbackWithIndex(int index) {
        MoveTimeoutExpired timerPacket = new MoveTimeoutExpired();
        timerPacket.setPlayerId(index);
        sendEventToView(timerPacket);
        ControllerEndTurn event = new ControllerEndTurn();
        event.setPlayerId(index);
        visit(event);
    }
}
