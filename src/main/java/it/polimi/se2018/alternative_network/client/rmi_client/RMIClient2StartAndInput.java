package it.polimi.se2018.alternative_network.client.rmi_client;

import it.polimi.se2018.alternative_network.client.AbstractClient2;
import it.polimi.se2018.alternative_network.newserver.rmi.RMIServerInterfaceSeenByClient;
import it.polimi.se2018.list_event.event_received_by_server.EventServer;
import it.polimi.se2018.list_event.event_received_by_server.ServerVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_game.EventController;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGame;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.EventPreGameVisitor;
import it.polimi.se2018.list_event.event_received_by_server.event_for_server.event_pre_game.LoginRequest;
import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.AskLogin;
import it.polimi.se2018.list_event.event_received_by_view.event_from_controller.game_state.ConnectionDown;
import it.polimi.se2018.utils.TimerCallback;
import it.polimi.se2018.utils.TimerThread;
import it.polimi.se2018.view.UIInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author DavideMammarella
 */
public class RMIClient2StartAndInput extends AbstractClient2 implements ServerVisitor, EventPreGameVisitor{

    private RMIServerInterfaceSeenByClient serverRMI;
    private RMIClientInterface client; //instance of the stud
    private RMIClientInterface remoteRef; //remoteRef of the stud in the server used for login

    public RMIClient2StartAndInput(String serverIpAddress, int serverPort, UIInterface view) {
        super(serverIpAddress, serverPort, view);
    }

    private RMIServerInterfaceSeenByClient getServerRMI() {
        return serverRMI;
    }

    @Override
    public void connectToServer2() {
        try {
            // Chiedo al Registry ( in esecuzione su localhost alla porta di default ) di localizzare 'Server' e restituirmi il suo Stub
            // Naming.lookup(//host:port/name) host è l'ip, port è la porta  name è il nome del servizio offerto dall'host
            serverRMI = (RMIServerInterfaceSeenByClient) Naming.lookup("//" + ipHost + ":" + port + "/MyServer");
            //ping
            serverRMI.sayHelloToGatherer();
            //create the stud
            client = new RemoteClientReceiver(this);
            //Network handler non estende UnicastRemoteObject, quindi devo creare lo Skeleton sfruttando UnicastRemoteObject.exportObject
            //che esporta la porta CLIENT da utilizzare per la connessione e il riferimento al'oggetto client da esportare.
            // nota_1: se al server venisse passato "client", sarebbe una sua copia ( la deserializzazione della serializzazione di client )
            //         per questo si crea e si passa al server un riferimento remoto, che non serve essere esportato sul Registry come servizio.
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
            AskLogin packet = new AskLogin();
            this.sendEventToUIInterface2(packet);
        } catch (NotBoundException ex) {
            ConnectionDown packet = new ConnectionDown("Il server è stato raggiunto, ma non c'è il servizio richiesto", false);
            view.showEventView(packet);
        } catch (RemoteException ex) {
            ConnectionDown packet = new ConnectionDown("Non è stato possibile contattare il server.", false);
            view.showEventView(packet);
        } catch (MalformedURLException ex) {
            ConnectionDown packet = new ConnectionDown("URL non corretto.", false);
            view.showEventView(packet);
        }
    }


    @Override
    public void shutDownClient2() {
        try {
            serverRMI.sayHelloToGatherer();
            UnicastRemoteObject.unexportObject(client, true);

            ConnectionDown packet = new ConnectionDown("Eri già stato scollegato dal server.", true);
            view.showEventView(packet);
        } catch (RemoteException ex) {

            ConnectionDown packet = new ConnectionDown("Eri già stato scollegato dal server.", true);
            view.showEventView(packet);
        }
    }

    @Override
    public void sendEventToController2(EventServer eventController) {
        eventController.acceptGeneric(this);

    }

    @Override
    public void sendEventToUIInterface2(EventClient event) {
        view.showEventView(event);

    }

    @Override
    public void visit(EventController event) {
        Runnable exec = () -> {
            Thread.currentThread().setName("Visitor Handler: " + event.getClass().getSimpleName());
            try {
                getServerRMI().sendEventToController(event);
            } catch (RemoteException ex) {
                ConnectionDown packet = new ConnectionDown("La connessione è caduta.", false);
                view.showEventView(packet);
            }
        };
        Thread currentTask = new Thread(exec);
        currentTask.start();

    }

    @Override
    public void visit(EventPreGame event) {
        event.acceptPreGame(this);
    }

    @Override
    public void visit(LoginRequest event) {
        try {
            serverRMI.addClient(event.getNickname(), client);
        } catch (RemoteException ex) {
            ConnectionDown packet = new ConnectionDown("La connessione è caduta.", false);
            view.showEventView(packet);
        }

    }

}