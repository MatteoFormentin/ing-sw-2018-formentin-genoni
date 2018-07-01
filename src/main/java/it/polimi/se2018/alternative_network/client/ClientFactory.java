package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Operazioni da esportare nel client
 *
 * 1 creare l'Abstract client concreto installando la view, l'host e la porta
 * 2 connettere l'abstract client al server, se rifiutato ricreare un nuovo abstract client
 * 3 effettuare il login
 * (4 utilizzare sendEventToController per inviare informazioni al giocatore)
 * 5 chiamare disconnect per disconnettersi legalmente
 *
 */
public class ClientFactory {

    private static ClientFactory instance;
    private UIInterface view;
    private String IP_SERVER;
    private int RMI_PORT;
    private int SOCKET_PORT;
    private static AbstractClient2 abstractClient;

    public ClientFactory(UIInterface view) {
        this.view = view;
        loadDefault();
    }

    public AbstractClient2 createClient(String serverIpAddress, int port, int rmi0socket1) {
        if (serverIpAddress.equals("") || serverIpAddress.equals("0")) serverIpAddress = IP_SERVER;
        if (rmi0socket1 == 0) {
            if (port == 0) abstractClient = new RMIClient2StartAndInput(serverIpAddress, RMI_PORT, view);
            else abstractClient = new RMIClient2StartAndInput(serverIpAddress, port, view);
        } else if (rmi0socket1 == 1) {
            if (port == 0) abstractClient = new SocketClient(null, serverIpAddress, SOCKET_PORT);
            else abstractClient = new SocketClient(null, serverIpAddress, port);
        }
        return abstractClient;
    }

    /**
     * instruction for make it work
     * @param args
     */
    public static void main(String[] args) {
        instance = new ClientFactory( new CliController());
        String IP_SERVER = "localhost";
        int RMI_PORT = 31415;
       try {
            Properties configProperties = new Properties();
           String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);
            configProperties.load(inputConnection);
            RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            IP_SERVER = configProperties.getProperty("SERVER_ADDRESS");
        } catch (IOException e) {
            System.out.println("La configurazione non può essere impostata da file, verrà caricata quella di default");
        }
        abstractClient =instance.createClient(IP_SERVER, RMI_PORT, 0);

        CliParser input = new CliParser();
        System.out.println("Digita 0 per collegarti al server, 1 per uscire: ");
        if (input.parseInt(1) == 0) {
            try {
                abstractClient.connectToServer2();
                try {
                    System.out.println("inserici il nome: ");
                    abstractClient.login2(input.parseNickname());
                } catch (PlayerAlreadyLoggedException ex) {
                    System.out.println("il nome da te inserito è già stato scelto");
                } catch (RoomIsFullException ex) {
                    System.out.println("la stanza è piena");
                } catch (ConnectionProblemException ex) {
                    System.out.println("Caduta la linea");
                }
            } catch (ConnectionProblemException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("quando vuoi digita 0 per scollegarti dal");
        if (input.parseInt(1) == 0) abstractClient.shutDownClient2();
    }

    public void loadDefault() {
        try {
            Properties configProperties = new Properties();
            String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);
            configProperties.load(inputConnection);
            IP_SERVER = configProperties.getProperty("SERVER_ADDRESS");
            RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            SOCKET_PORT = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
        } catch (IOException ex) {
            IP_SERVER = "localhost";
            RMI_PORT = 1099;
            SOCKET_PORT = 1090;
        }
    }
}
