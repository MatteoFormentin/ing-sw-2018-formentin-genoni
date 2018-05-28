package it.polimi.se2018.view.cli;

import it.polimi.se2018.event.list_event.EndTurn;
import it.polimi.se2018.event.list_event.EventView;
import it.polimi.se2018.event.list_event.InsertDice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.network.client.Client;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.view.UIInterface;

import java.awt.*;

public class CliController implements UIInterface {

    private CliMessage cliMessage;
    private CliParser cliParser;
    private ClientController client;
    private Player player;
    private ObjectivePublicCard[] publicCard;

    private DiceStack dicePool;
    private DiceStack[] roundTrack;
    private ToolCard[] toolCard;
    private Player[] opponentPlayers;


    public CliController(ClientController clientController) {
        client = clientController;
        cliMessage = new CliMessage();
        cliParser = new CliParser();
        cliMessage.splashScreen();
        cliParser.readSplash();
        initConnection();
        login();
    }

    public void updatePlayer(Player player) {
        this.player = player;
    }

    public void updateDicePool(DiceStack dicePool) {
        this.dicePool = dicePool;
    }

    public void updateRoundTrack(DiceStack[] roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void updatePublicCard(ObjectivePublicCard[] publicCard) {
        this.publicCard = publicCard;
    }

    public void updateToolCard(ToolCard[] toolCard) {
        this.toolCard = toolCard;
    }

    public void updateOpponentPlayer(Player[] opponentPlayers) {
        this.opponentPlayers = opponentPlayers;
    }

    public void showMessage(EventView eventView) {

    }

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
                EventView packet = new EndTurn();
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
