package it.polimi.se2018.view.cli;

import it.polimi.se2018.list_event.event_received_by_controller.*;
import it.polimi.se2018.list_event.event_received_by_view.*;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;


public class CliController implements UIInterface,ViewVisitor {

    private CliMessage cliMessage;
    private CliParser cliParser;
    private ClientController client;
    private Player player;
    private ObjectivePublicCard[] publicCard;

    private DiceStack dicePool;
    private DiceStack[] roundTrack;
    private ToolCard[] toolCard;
    private Player[] opponentPlayers;

    private int playerId;


    public CliController(ClientController clientController) {
        client = clientController;
        cliMessage = new CliMessage();
        cliParser = new CliParser();
        cliMessage.splashScreen();
        cliParser.readSplash();
        initConnection();
        login();
    }

    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************
    //*****************************************Visitor Pattern************************************************************************

    public void showMessage(EventView EventView) {
        EventView.accept(this);
    }

    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************
    //*******************************************Visit for Controller event*******************************************************************************



    @Override
    public void visit(UpdateInitialWindowPatternCard event) {
        for (WindowPatternCard card : ((UpdateInitialWindowPatternCard) event).getInitialWindowPatternCard()) {
            cliMessage.showWindowPatternCard(card);
        }

        cliMessage.showInitialWindowPatternCardSelection();
        int selection = cliParser.parseInt(4);
        EventController packet = new SelectInitialWindowPatternCardController();
        packet.setPlayerId(playerId);
        ((SelectInitialWindowPatternCardController) packet).setSelectedIndex(selection);
        client.sendEventToController(packet);
    }

    @Override
    public void visit(StartGame event) {
        //TODO mostra info sul gioco (numeri giocatori, stato connessione)
    }

    @Override
    public void visit(WaitYourTurn event) {
        cliMessage.showWaitYourTurnScreen();
    }

    /**
     * Method to handle the start of the turn
     *
     * maybe should receive and process some attribute for able/disable some options
     * for example: when you place one die the next event from the controller
     * set the event's boolean insert die to false and the menu won't show this option
     *
     * @param event of type startPlayer
     */
    @Override
    public void visit(StartPlayerTurn event) {
        turn();
    }

    @Override
    public void visit(EndGame event) {
        //TODO when the player received the update show the point of all the players
        //it's cool too if we can make the point accumulated by each Player a class with 7 different fields:
        // private object, the 3 public object, the remain favor token, the lost points and the total of all

    }

    /**
     * Receive the Event for select which dice take from the draft pool and send the packet to the controller(throught)
     * @param event
     */
    @Override
    public void visit(SelectDiceFromDraftpool event){
        cliMessage.showDiceStack(dicePool);
        int diceIndex = cliParser.parseInt();
        SelectDiceFromHandController packet = new SelectDiceFromHandController();
        packet.setPlayerId(playerId);
        packet.setIndex(diceIndex);
        client.sendEventToController(packet);
    };

    @Override
    public void visit(SelectCellOfWindowView event) {
        cliMessage.showInsertDiceRow();
        int row = cliParser.parseInt();
        cliMessage.showInsertDiceColumn();
        int column = cliParser.parseInt();
        SelectCellOfWindowController packet = new SelectCellOfWindowController();
        packet.setPlayerId(playerId);
        packet.setLine(row);
        packet.setColumn(column);
        client.sendEventToController(packet);
    }

    @Override
    public void visit(SelectToolCard event){
        // TODO mostrare inserimento dell'indice da 0 a 2 (oppure se decrementato da 1 a 3)

    }

    @Override
    public void visit(ShowErrorMessage event){
        //TODO printare a schermo il messaggio d'errore l'evento contiene sia l'eccezione che il messagio scegli quale vuoi ed elimina l'altro
      }

    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
    //*******************************************Visit for model event*******************************************************************************
  /*  public void visit(UpdateAllToolCard event);
    public void visit(UpdateSingleToolCard event);
    public void visit(UpdateDicePool event);
    public void visit(UpdateInitialWindowPatternCard event);

    public void visit(UpdateSinglePlayerHand event);
    public void visit(UpdateAllPublicObject event);
    public void visit(UpdateSingleCell event);
    public void visit(UpdateSinglePlayerTokenAndPoints event);
    public void visit(UpdateSinglePrivateObject event);
    public void visit(UpdateSingleTrunRoundTrack event);
    public void visit(UpdateSingleWindow event);*/


    //OPERATION HANDLER
    private void initConnection() {
        boolean flag = false;
        do {
            cliMessage.showIpRequest();
            String ip = cliParser.parseIp();

            cliMessage.showPortRequest();
            int port = cliParser.parseInt();

            if (client.startRMIClient(ip, port)) {
                flag = true;
                cliMessage.showConnectionSuccessful();
                cliMessage.println();
            } else {
                cliMessage.showConnectionFailed();
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

    private void insertPlayerData() {
        cliMessage.showInsertNickname();
        String name = cliParser.parseNickname();
        //invio al server
    }

    private void turn() {
        //TODO far rivedere il menù dopo un azione con opzioni disabilitate
        cliMessage.showYourTurnScreen();
        cliMessage.showWindowPatternCard(player.getPlayerWindowPattern());
        cliMessage.showMainMenu();
        int option = cliParser.parseInt();

        switch (option) {
            //Place dice
            case 1:
                EventController packet = new InsertDiceController();
                packet.setPlayerId(playerId);
                client.sendEventToController(packet);
                //invio evento piaza insert dice al controller
                //controller flag Mossa normale
                //rispondo con il visitor con select drafpool
                //view da caontroller l'indice controlla flag in base a quello risponde alla view select Cell of window
                //la view
                break;

            //Use tool card
            case 2:
                break;

            //End turn
            case 3:
                EventController packet2 = new EndTurnController();
                packet2.setPlayerId(playerId);
                client.sendEventToController(packet2);
                cliMessage.showWaitYourTurnScreen();
                break;

            //Show public object
            case 4:
                for (ObjectivePublicCard card : publicCard) {
                    cliMessage.showObjectivePublicCard(card);
                }
                break;

            //Show private object
            case 5:
                cliMessage.showObjectivePrivateCard(player.getPrivateObject());
                break;

            //Show opponents window pattern card
            case 6:
                for (Player p : opponentPlayers) {
                    cliMessage.showWindowPatternCard(p.getPlayerWindowPattern());
                }
                break;
        }
    }

}
