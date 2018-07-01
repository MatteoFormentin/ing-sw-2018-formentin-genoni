package it.polimi.se2018.alternative_network.client;

import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.client.AbstractClient;
import it.polimi.se2018.alternative_network.newserver.rmi.RMIServerInterfaceSeenByClient;
import it.polimi.se2018.view.UIInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Operazioni da esportare nel client
 *
 * 2 connettere l'abstract client al server, se rifiutato ricreare un nuovo abstract client
 * 3 effettuare il login
 * (4 utilizzare sendEventToController per inviare informazioni all'UIINTERFACE)
 * 5 chiamare disconnect per disconnettersi legalmente
 * 6 utilizzare shutDownClient2 per disconnettersi brutalmente
 *
 */
public class RMIClient2StartAndInput extends AbstractClient implements AbstractClient2 {
    private RMIClient2StartAndInput instanceClient;
    private RMIServerInterfaceSeenByClient RMIServer;
    private String IP_SERVER;
    private int RMI_PORT;
    private int SOCKET_PORT;

    //the Remote class that build the connection with the server
    private RMIClientInterface client; //instance of the stud expoted
    private RMIClientInterface remoteRef; //remoteRef of the stud in the server used for login
    private String nickname;

    //TODO da spostare nel nell'abstract client


    public RMIClient2StartAndInput(String serverIpAddress, int serverPort, UIInterface view) {
        super(null,serverIpAddress,serverPort,view);
    }


    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************
    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************
    //************************************ METODI PER INTERAGIRE CON IL RMIGATHERER ***************************************

    @Override
    public void connectToServer2() throws ConnectionProblemException{
        try {
            // Chiedo al Registry ( in esecuzione su localhost alla porta di default ) di localizzare 'Server' e restituirmi il suo Stub
            // Naming.lookup(//host:port/name) host è l'ip, port è la porta  name è il nome del servizio offerto dall'host
            RMIServer = (RMIServerInterfaceSeenByClient) Naming.lookup("//"+getServerIpAddress()+":"+getServerPort()+"/MyServer");
            //ping
            RMIServer.sayHelloToGatherer();
            //create the stud
            client = new RemoteClientReceiver(this);
            //Network handler non estende UnicastRemoteObject, quindi devo creare lo Skeleton sfruttando UnicastRemoteObject.exportObject
            //che esporta la porta CLIENT da utilizzare per la connessione e il riferimento al'oggetto client da esportare.
            // nota_1: se al server venisse passato "client", sarebbe una sua copia ( la deserializzazione della serializzazione di client )
            //         per questo si crea e si passa al server un riferimento remoto, che non serve essere esportato sul Registry come servizio.
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            //creo l'oggetto client
        } catch (NotBoundException ex) {
            System.err.println("connectToServer RMI dice: " + "non è possibile raggiungere il registry");
            ex.printStackTrace();
            getView().errPrintln(ex.getMessage());
            throw new ConnectionProblemException(ex.getMessage());
        } catch (RemoteException ex) {
            System.err.println("connectToServer RMI dice: " + "registry could not be contacted"+"\nImpossibile connettersi al server");
            ex.printStackTrace();
            getView().errPrintln(ex.getMessage());
            throw new ConnectionProblemException(ex.getMessage());
        } catch(MalformedURLException ex){
            System.err.println("connectToServer RMI dice: " + "if the name of the URL is not an appropriately");
            ex.printStackTrace();
            getView().errPrintln(ex.getMessage());
            throw new ConnectionProblemException(ex.getMessage());
        }
    }

    @Override
    public void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException {
        try{
            RMIServer.addClient(nickname,remoteRef);
        }catch (PlayerAlreadyLoggedException ex){
            System.err.println("connectToServer RMI dice: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }catch (RoomIsFullException ex){
            System.err.println("connectToServer RMI dice: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }catch (RemoteException ex){
            System.err.println("connectToServer RMI dice: " + "registry could not be contacted");
            ex.printStackTrace();
            throw new ConnectionProblemException(ex.getMessage());
        }
    }


    @Override
    public void sendEventToController2(EventController eventController)throws ConnectionProblemException{
        try{
            RMIServer.sendEventToController(eventController);
        }catch(RemoteException ex){
            getView().errPrintln("\n\n\nsendEventToController2\nYou can't contact the RMI server: "+ ex.getMessage()+"\n\n");
            throw new ConnectionProblemException(ex.getMessage());
        }
    }

    @Override
    public void shutDownClient2() {
        try {
            RMIServer.sayHelloToGatherer();
            UnicastRemoteObject.unexportObject(client, true);
        }catch(NoSuchObjectException ex){
            getView().errPrintln("\n\n\nshutDownClient2\nthe Object Remote doesn't exist "+ ex.getMessage()+"\n\n");
        }catch(RemoteException ex){
            getView().errPrintln("\n\n\nshutDownClient2\nYou can't contact the RMI server: "+ ex.getMessage()+"\n\n");
            System.err.println("Sei già stato disconnesso");
            try{
                UnicastRemoteObject.unexportObject(client, true);
            }catch(NoSuchObjectException ex2){
                getView().errPrintln("\n\n\nshutDownClient2\nthe Object Remote doesn't exist  "+ ex.getMessage()+"\n\n");
            }
        }
    }

    @Override
    public void sendEventToUIInterface2(EventView event) {
        getView().showMessage(event);
    }

//*************************** OLD DA RIMUOVERE ********************************************

    @Override
    public void connectToServer() throws Exception {

    }

    @Override
    public void login(String nickname) throws RemoteException, PlayerAlreadyLoggedException {

    }

    @Override
    public void sendEventToController(EventController eventController) throws RemoteException {

    }

    @Override
    public void disconnect() throws RemoteException {

    }
}