package it.polimi.se2018.network.server.new_rmi;

import it.polimi.se2018.exception.network_exception.PlayerAlreadyLoggedException;
import it.polimi.se2018.list_event.event_received_by_controller.EventController;
import it.polimi.se2018.list_event.event_received_by_view.EventView;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.server.ServerController;
import it.polimi.se2018.network.server.socket.ClientGatherer;
import it.polimi.se2018.network.server.socket.SocketServer;
import it.polimi.se2018.view.cli.CliParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.LinkedList;
import java.util.Properties;

public class Server implements ServerController {
    //info for the operation of the server
    private static Server instance;
    private RMIClientGatherer RMIImplementation;
    private ClientGatherer SocketImplementation;
    private String IP_SERVER;
    private int RMI_PORT;
    private int SOCKET_PORT;
    private CliParser input;

    //info for the player connected to a gameBoard
    private LinkedList<RemotePlayer> players;

    public LinkedList<RemotePlayer> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        if (instance == null) {
            instance = new Server();
            instance.start();
        }
    }

    /**
     * Choice of the port, creation of the Registry, start of the RmiServer and SocketServer
     */
    public void start() {
        input = new CliParser();
        boolean flag = setUPConnection();
        if (!flag) {
            System.out.println("inserisci l'ip: ");
            IP_SERVER = input.parseIp();
            if (IP_SERVER.equals("0")) IP_SERVER = "localhost";
            System.out.println("inserisci la porta per RMI: ");
            RMI_PORT = input.parsePort(1024, 65535, 0);
            System.out.println("inserisci la porta per SOCKET: ");
            SOCKET_PORT = input.parsePort(1024, 65535, RMI_PORT);
        }

        //after the setup of the server start the clients gatherer (RMI and Socket)
        flag = true;
        try {
            //Creo il regustry in modo da tenere occupata la porta
            LocateRegistry.createRegistry(RMI_PORT);
            // Creo una istanza di RMIClientGatherer, che rappresenta il "servizio" che voglio offrire ai client
            RMIImplementation = new RMIClientGatherer(this);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            System.out.println("Registry già presente!");
            System.out.println("La creazione del server viene interrotta");
            flag = false;
        }
        if (flag) {
            //non è ancora settato
            flag = false;

            //setup RMI client gatherer
            if(startRmiGatherer()){
                //TODO nel caso in cui la porta sia occupata che succede?
                startSocketGatherer();
            }else{
                flag=false;
            }
            if (flag) System.out.println("Il setter del server si è chiuso correttamente");
            else System.out.println("Il setter del server si è stato arrestato");
        }
    }

    //****************************** Event from the to 2 listener *******************************************************
    //****************************** Event from the to 2 listener *******************************************************
    //****************************** Event from the to 2 listener *******************************************************

    @Override
    public boolean login(RemotePlayer remotePlayer) throws PlayerAlreadyLoggedException{
        //check dei nomi

        for(int i=0; i<players.size();i++){
            if(remotePlayer.getNickname().equals(players.get(i).getNickname())){
                if(players.get(i).getPlayerRunning()){

                }else{

                }
            }
        }
        return false;
    }

    @Override
    public void sendEventToController(EventController eventController) {

    }

    @Override
    public void startGame() {
        for(int i=0; i<players.size();i++){
            try {
                players.get(i).sayHelloClient();
            }catch (RemoteException e){
                //TODO rimuovere il giocatore
            }
        }
        //TODO se tutto a posto parti con la partita
    }

    @Override
    public void joinGame(RemotePlayer remotePlayer) {

    }

    @Override
    public RemotePlayer searchPlayerById(int id) {
        return null;
    }

    @Override
    public void sendEventToView(EventView eventView) {

    }


    private boolean setUPConnection() {
        System.out.print("Digita 0 per settare l'ip e porta da config o default, 1 per settare manualmente");
        if (input.parseInt(1) == 0) {
            try {
                Properties configProperties = new Properties();
                String connectionConfig = "src/main/resources/configurations/connection_configuration.properties";
                FileInputStream inputConnection = new FileInputStream(connectionConfig);
                configProperties.load(inputConnection);
                RMI_PORT = Integer.parseInt(configProperties.getProperty("RMI_PORT"));
                IP_SERVER = configProperties.getProperty("SERVER_ADDRESS");
                SOCKET_PORT = Integer.parseInt(configProperties.getProperty("SOCKET_PORT"));
                System.out.println("SERVER_ADDRESS set to " + configProperties.getProperty("SERVER_ADDRESS"));
                System.out.println("RMI port set to " + configProperties.getProperty("RMI_PORT"));
                return true;
            } catch (IOException e) {
                System.out.println("La configurazione non può essere impostata da file, verrà caricata quella di default");
                IP_SERVER = "localhost";
                RMI_PORT = 1099;
                SOCKET_PORT = 1090;
                System.out.println("Digitare 0 per confermare 1 per inserirla manualmente");
                return input.parseInt(1) == 0;
            }
        }
        return false;
    }
    private boolean startRmiGatherer(){
        try {
            // A questo punto comunico al Registry che ho un nuovo servizio da offrire ai client passando a  Naming.rebind(..) :
            //  - una stringa di tipo "//host:port/name" host:port identifica l'indirizzo del Registry, se port è omesso viene utilizzata la porta di default: 1099
            //    >  name, a nostra  scelta, identifica il servizio ed è univoco all'interno del Registry avviato su host:port
            //  - l'oggetto RMIImplementation
            Naming.rebind("//" + IP_SERVER + ":" + RMI_PORT + "/MyServer", RMIImplementation);
            return true;
        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }
        return false;
    }
    private boolean startSocketGatherer(){
        SocketImplementation = new ClientGatherer(SOCKET_PORT, this);
        new Thread(SocketImplementation).start();
        return true;
    }

    public RemotePlayer searchPlayerLogged(RMIPlayer client){
        return null;
    }
}
