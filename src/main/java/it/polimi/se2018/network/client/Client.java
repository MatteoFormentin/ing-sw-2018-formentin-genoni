package it.polimi.se2018.network.client;

/**
 * Class that define the client side of the game.
 *
 * @author Davide Mammarella
 */
public class Client {

    // Classe che rappresenta il client selezionato
    private AbstractClient client;

    // Interfaccia utilizzata per inviare eventi a CLI o GUI (a seconda del tipo di interfaccia utilizzata per il client)
    private ClientController ui;

    // Indirizzo su cui le comunicazioni sono aperte a lato server
    private static final String HOST = "localhost";

    // Porta utilizzata per la comunicazione socket
    private static final int SERVER_SOCKET_PORT = 61803;

    // Porta utilizzata per la comunicazione RMI
    //private static final int SERVER_RMI_PORT = ;

    // Flag utile per determinare lo stato della connessione
    private boolean isLogged;

    // Nome del giocatore corrente
    private String username;

    // Turno della partita (da 1 a 9)
    private int turn;

    public Client(ClientController ui){
        this.ui = ui;
        username = "anonymous";
        isLogged = false;

        // COSE (RISORSE, DADI, ECC) DEI GIOCATORI (AGGIORNATE ALL'ULTIMO AGGIORNAMENTO RICEVUTO DAL SERVER

        this.turn = 0;

        //responseMap = new HashMap<>();
       //loadResponses();
    }

    // handle response gestirà la risposta ricevuta dal Server ed invocherà il metodo associatogli nella MAPPA DELLE RESPONSE

    // METODI CHE SI SCATENANO QUANDO IL SERVER NOTIFICA UN PRECISO EVENTO

    //METODO INVOCATO DAL SERVER QUANDO L'AZIONE RICHIESTA DAL GIOCATORE VIENE ACCETTATA

}
