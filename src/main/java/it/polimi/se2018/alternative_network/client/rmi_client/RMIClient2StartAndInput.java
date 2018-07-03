package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.exception.network_exception.client.ConnectionProblemException;
import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.exception.network_exception.RoomIsFullException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
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
 * (4 utilizzare sendEventToController per inviare informazioni dall'UI interface al server)
 * 5 chiamare disconnect per disconnettersi legalmente(richiedendo il kick all'interface)
 * 6 utilizzare shutDownClient2 per disconnettersi brutalmente
 *
 */
public class RMIClient2StartAndInput implements AbstractClient2 {

    private UIInterface view;
    private String serverIpAddress;
    private int serverPort;
    private RMIServerInterfaceSeenByClient serverRMI;
    private RMIClientInterface client; //instance of the stud
    private RMIClientInterface remoteRef; //remoteRef of the stud in the server used for login

    public RMIClient2StartAndInput(String serverIpAddress, int serverPort, UIInterface view) {
        this.serverIpAddress = serverIpAddress;
        this.serverPort = serverPort;
        this.view=view;
    }

    @Override
    public void connectToServer2() throws ConnectionProblemException{
        try {
            // Chiedo al Registry ( in esecuzione su localhost alla porta di default ) di localizzare 'Server' e restituirmi il suo Stub
            // Naming.lookup(//host:port/name) host è l'ip, port è la porta  name è il nome del servizio offerto dall'host
            serverRMI = (RMIServerInterfaceSeenByClient) Naming.lookup("//"+serverIpAddress+":"+serverPort+"/MyServer");
            //ping
            serverRMI.sayHelloToGatherer();
            //create the stud
            client = new RemoteClientReceiver(this);
            //Network handler non estende UnicastRemoteObject, quindi devo creare lo Skeleton sfruttando UnicastRemoteObject.exportObject
            //che esporta la porta CLIENT da utilizzare per la connessione e il riferimento al'oggetto client da esportare.
            // nota_1: se al server venisse passato "client", sarebbe una sua copia ( la deserializzazione della serializzazione di client )
            //         per questo si crea e si passa al server un riferimento remoto, che non serve essere esportato sul Registry come servizio.
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            //creo l'oggetto client
        } catch (NotBoundException ex) {
            throw new ConnectionProblemException("Il server è stato raggiunto, ma non ci sono Servizi disponibili su questo server");
        } catch (RemoteException ex) {
            throw new ConnectionProblemException("Non è stato possibile contattare il server, controllare i firewall");
        } catch(MalformedURLException ex){
            throw new ConnectionProblemException("URL non corretto.");
        }
    }

    @Override
    public void login2(String nickname) throws ConnectionProblemException, PlayerAlreadyLoggedException, RoomIsFullException {
        try{
            serverRMI.addClient(nickname,remoteRef);
        } catch (RemoteException ex){
            throw new ConnectionProblemException("La connessione è caduta.");
        }
    }


    @Override
    public void sendEventToController2(EventController eventController)throws ConnectionProblemException{
        try{
            serverRMI.sendEventToController(eventController);
        }catch(RemoteException ex){
            throw new ConnectionProblemException("La connessione è caduta.");
        }
    }

    @Override
    public void shutDownClient2() {
        try {
            serverRMI.sayHelloToGatherer();
            UnicastRemoteObject.unexportObject(client, true);
        }catch(NoSuchObjectException ex){
            view.errPrintln("shutDownClient: ->the Object Remote doesn't exist");
        }catch(RemoteException ex){
            view.errPrintln("shutDownClient -> Sei già disconnesso");
        }
    }

    @Override
    public void sendEventToUIInterface2(EventView event) {
        view.showMessage(event);
    }
}