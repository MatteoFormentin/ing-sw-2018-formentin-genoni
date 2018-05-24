package it.polimi.se2018.network.client;

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
    private static final String HOST = "localhost";
    //Porta su cui si appoggierà la comunicazione socket
    private static final int SERVER_SOCKET_PORT = 16180;
    //Porta su cui si appoggierà la comunicazione RMI
    private static final int SERVER_RMI_PORT = 31415;

    // Nome del giocatore corrente
    private String username;
    // Turno della partita (da 1 a 9)
    private int turn;

    // MANCA TUTTO
    public Client(){
        username = "zero";

        // COSE (RISORSE, DADI, ECC) DEI GIOCATORI (AGGIORNATE ALL'ULTIMO AGGIORNAMENTO RICEVUTO DAL SERVER)

        this.turn = 0;
    }

}
