package it.polimi.se2018.network.client.new_rmi;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.network.client.ClientController;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import it.polimi.se2018.network.server.new_rmi.RMIServerInterfaceSeenByClient;
import org.omg.PortableInterceptor.INACTIVE;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClientStartAndInput extends AbstractClient implements IRMIClient {
    private static RMIClientStartAndInput instanceClient;
    private static RMIClientInterface instance;
    private static RMIServerInterfaceSeenByClient RMIServer;
    private String nickname;

    public RMIClientStartAndInput(ClientController clientController, String serverIpAddress, int serverPort) {
        super(clientController, serverIpAddress, serverPort);
    }

    public static void main(String[] args) {
        instanceClient = new RMIClientStartAndInput(null, null, 0);
        instanceClient.connectToServer();
    }

    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************
    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************
    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************

    @Override
    public void connectToServer() {
        try {
            //TODO Controllare se necessario Registry registry = LocateRegistry.getRegistry(host);
            // TODO e fare Hello stub = (Hello) registry.lookup("Hello");  String response = stub.sayHelloServer();
            // Chiedo al Registry ( in esecuzione su localhost alla porta di default ) di localizzare 'Server'
            // e restituirmi il suo Stub
            // Naming.lookup(//host:port/name)
            // host è l'ip, port è la porta
            // name è il nome del servizio offerto dall'host
            RMIServer = (RMIServerInterfaceSeenByClient) Naming.lookup("//localhost:31415/MyServer");
            String response = RMIServer.sayHelloServer();
            System.err.println("connectToServer RMI dice: " + response);
            //creo l'oggetto client
        } catch (MalformedURLException e) {
            System.err.println("connectToServer RMI dice: " + "URL non trovato!");
        } catch (RemoteException e) {
            System.err.println("connectToServer RMI dice: " + "Errore di connessione: lookup, Non è stato trovato il server." +
                    "L'ip o la porta sono sbagliati");
            System.err.println(e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("connectToServer RMI dice: " + "Non è stato ricevuto il riferimento del server remoto");
        }

        //actual login

    }

    @Override
    public void login(String nickname) throws RemoteException, PlayerAlreadyLoggedException {
        try {
            RMIClientInterface client = new RMIClientImplementation();
            instance = client;
            //Network handler non estende UnicastRemoteObject, quindi devo creare lo Skeleton sfruttando UnicastRemoteObject.exportObject
            //che esporta la porta CLIENT da utilizzare per la connessione e il riferimento al'oggetto client da esportare.
            // nota_1: se al server venisse passato "client", sarebbe una sua copia ( la deserializzazione della serializzazione di client )
            //         per questo si crea e si passa al server un riferimento remoto, che non serve essere esportato sul Registry come servizio.
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            RMIServer.login(nickname, instance);
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: login");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {
        RMIServer.sendEventToController(eventController);
    }

    @Override
    public void disconnect() throws RemoteException {
        RMIServer.disconnect(nickname, instance);
    }

    //************************************ METODI PER INTERAGIRE CON LA VIEW ******************************************
    //************************************ METODI PER INTERAGIRE CON LA VIEW ******************************************
    //************************************ METODI PER INTERAGIRE CON LA VIEW ******************************************
    @Override
    public void sendEventToView(EventView eventView) throws RemoteException {

    }
}
