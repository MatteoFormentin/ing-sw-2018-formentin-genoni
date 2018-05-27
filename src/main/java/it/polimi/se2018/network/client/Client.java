package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.rmi.RMIClient;

/**
 * Class based on the Abstract Factory Design Pattern.
 * This class define the client side of the game.
 * This class implements ClientController to have basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public class Client implements ClientController{

    // Classe che rappresenta il client selezionato
    private AbstractClient client;
    // Interfaccia utilizzata per inviare eventi a CLI o GUI (a seconda del tipo di interfaccia utilizzata per il client)
    //private ClientController ui;

    // INDIRIZZI PER LA COMUNICAZIONE

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static final String serverAddress = "localhost";
    //Porta su cui si appoggierà la comunicazione socket
    private static final int serverSocketPort = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    private static final int serverRMIPort = 31415;

    // Nome del giocatore corrente
    private String nickname;
    // Turno della partita (da 1 a 9)
    private int turn;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    // MANCA TUTTO
    public Client(){
        nickname="Mr.Nessuno";
        this.turn = 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    // CLIENT STARTER
    //------------------------------------------------------------------------------------------------------------------

    // aggiungere porta socket se aggiungi socket
    public static void main(String[] args){
        String serverIpAddress = serverAddress;
        int rmiPort = serverRMIPort;

        try {
            serverIpAddress = args[0];
            rmiPort = Integer.parseInt(args[0]);
            //ClientController.Client(serverIpAddress,rmiPort);
        } catch (Exception e){
            System.exit(0);
        }
    }

    // AGGIUNGI int socketPort se aggiungi socket
    // Se aggiungi socket qui ci dovrà essere la selezione, data da cli o gui fra RMI o Socket ed in base alla scelta bisogna far partire connessioni diverse
    public void startClient(String serverIpAddress, int rmiPort){
        startRMIClient(serverIpAddress, rmiPort);
    }

    private boolean startRMIClient (String serverIpAddress, int rmiPort){
        client=new RMIClient(this,serverIpAddress,rmiPort);
        client.connectToServer();
        System.out.println("RMI connection estabilished...");
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    // GETTER
    //------------------------------------------------------------------------------------------------------------------

    // GETTER PER IL NICKNAME
    public String getNickname(){
        return this.nickname;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM CLIENT - CALL TO THE SERVER
    //------------------------------------------------------------------------------------------------------------------

    // AGGIUNGI EXCEPTION
    public void login(String nickname) throws Exception {
        try {
            client.login(nickname);
            this.nickname = nickname;
            System.out.println(nickname+"is logged.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // qui la perform game action

}
