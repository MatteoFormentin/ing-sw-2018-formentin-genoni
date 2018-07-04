package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClient2StartAndInput;
import it.polimi.se2018.alternative_network.client.socket.SocketClient2;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.view.UIInterface;
import it.polimi.se2018.view.cli.CliController;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Operazioni da esportare nel client
 * <p>
 * 1 creare l'Abstract client concreto installando la view, l'host e la porta
 * 2 connettere l'abstract client al server, se rifiutato ricreare un nuovo abstract client
 * 3 effettuare il login
 * (4 utilizzare sendEventToController per inviare informazioni al giocatore)
 * 5 chiamare disconnect per disconnettersi legalmente
 */
public class ClientFactory {

    private static ClientFactory instance;
    private String ipServer;
    private int rmiPort;
    private int socketPort;
    private AbstractClient2 abstractClient;

    private ClientFactory() {
        loadDefault();
    }

    public static ClientFactory getClientFactory() {
        if (instance == null) instance = new ClientFactory();
        return instance;
    }

    /**
     * instruction for rmi
     * @param
     *//*
    public static void main(String[] args) {
        instance = getClientFactory();
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
        AbstractClient2 abstractClient = instance.createClient(new CliController(), IP_SERVER, RMI_PORT, 0);

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
        System.out.println("quando vuoi digita 10 per scollegarti dal");
        if (input.parseInt(1) == 0) abstractClient.shutDownClient2();
    }*/

    private void loadDefault() {
        try {
            Properties configProperties = new Properties();
            String connectionConfig = "src/resources/configurations/connection_configuration.properties";
            FileInputStream inputConnection = new FileInputStream(connectionConfig);
            configProperties.load(inputConnection);
            ipServer = configProperties.getProperty("SERVER_ADDRESS");
            rmiPort = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
            socketPort = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
        } catch (IOException ex) {
            ipServer = "localhost";
            rmiPort = 31415;
            socketPort = 16180;
        }
    }

    public AbstractClient2 createClient(UIInterface view, String serverIpAddress, int port, int rmi0socket1) {
        if (serverIpAddress.equals("") || serverIpAddress.equals("0")) serverIpAddress = ipServer;
        if (rmi0socket1 == 0) {
            if (port == 0) abstractClient = new RMIClient2StartAndInput(serverIpAddress, rmiPort, view);
            else abstractClient = new RMIClient2StartAndInput(serverIpAddress, port, view);
        } else if (rmi0socket1 == 1) {
            if (port == 0) abstractClient = new SocketClient2(serverIpAddress, socketPort, view);
            else abstractClient = new SocketClient2(serverIpAddress, port, view);
        }
        return abstractClient;
    }

    /**
     * instruction of socket
     * @param args
     */

    public static void main(String[] args) {
        instance = getClientFactory();
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
        AbstractClient2 abstractClient = instance.createClient(new CliController(), IP_SERVER, RMI_PORT, 1);

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
        System.out.println("quando vuoi digita 10 per scollegarti dal");
        if (input.parseInt(1) == 0) abstractClient.shutDownClient2();
    }
}
