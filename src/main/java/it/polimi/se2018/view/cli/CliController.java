package it.polimi.se2018.view.cli;

import it.polimi.se2018.list_event.event_controller.*;
import it.polimi.se2018.list_event.event_view.*;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;

public class CliController implements UIInterface, ViewVisitor {

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


    //VISITOR PATTERN

    public void showMessage(EventView EventView) {
        EventView.accept(this);
    }


    @Override
    public void visit(EndGame event) {

    }

    @Override
    public void visit(InitialWindowPatternCard event) {
        for (WindowPatternCard card : ((InitialWindowPatternCard) event).getInitialWindowPatternCard()) {
            cliMessage.showWindowPatternCard(card);
        }

        cliMessage.showInitialWindowPatternCardSelection();
        int selection = cliParser.parseInt(4);
        EventController packet = new SelectInitialWindowPatternCard();
        packet.setPlayerId(playerId);
        ((SelectInitialWindowPatternCard) packet).setSelectedIndex(selection);
        client.sendEventToController(packet);
    }

    @Override
    public void visit(StartGame event) {

    }

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
        cliMessage.showYourTurnScreen();
        cliMessage.showWindowPatternCard(player.getPlayerWindowPattern());
        cliMessage.showMainMenu();
        int option = cliParser.parseInt();

        switch (option) {
            //Place dice
            case 1:
                insertDice();
                break;

            //Use tool card
            case 2:
                break;

            //End turn
            case 3:
                EventController packet = new EndTurn();
                //send packet
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

    private void insertDice() {
        cliMessage.showDiceStack(dicePool);
        int diceIndex = cliParser.parseInt();
        cliMessage.showInsertDiceRow();
        int row = cliParser.parseInt();
        cliMessage.showInsertDiceColumn();
        int column = cliParser.parseInt();

        InsertDice packet = new InsertDice();
        packet.setIndex(diceIndex);
        packet.setRow(row);
        packet.setColumn(column);

        //send packet
        //wait for server response
        //show ok-failed
    }
}
