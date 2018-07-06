package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.alternative_network.client.rmi_client.RMIClient2StartAndInput;
import it.polimi.se2018.alternative_network.client.socket.SocketClient2;
import it.polimi.se2018.view.UIInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Operazioni da esportare nel client
 * <p>
 * 1 creare l'Abstract client concreto installando la view, l'host e la porta
 * 2 connettere l'abstract client al server, se rifiutato ricreare un nuovo abstract client
 * 3 effettuare il login
 * (4 utilizzare sendEventToNetwork per inviare informazioni al giocatore)
 * 5 chiamare disconnect per disconnettersi legalmente
 *
 * @author DavideMammarella
 * @author Luca Genoni
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

    public AbstractClient2 createClient(UIInterface view, String serverIpAddress, int port, int rmi0socket1,boolean cli) {
        if (serverIpAddress == null || serverIpAddress.equals("0") || serverIpAddress.equals(""))
            serverIpAddress = ipServer;
        if (rmi0socket1 == 0) {
            if (port == 0) abstractClient = new RMIClient2StartAndInput(serverIpAddress, rmiPort, view);
            else abstractClient = new RMIClient2StartAndInput(serverIpAddress, port, view);
        } else if (rmi0socket1 == 1) {
            if (port == 0) abstractClient = new SocketClient2(serverIpAddress, socketPort, view);
            else abstractClient = new SocketClient2(serverIpAddress, port, view);
            if(cli){
                //TODO forse assegnare all'abstract client la nuova view
                // 1° solzione è questa
            }
        }
        return abstractClient;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
