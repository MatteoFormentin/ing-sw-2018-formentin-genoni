package it.polimi.se2018.network.client;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as client controller in AbstractClient.
 * Implemented by Client class in order to provide basic methods for RMI and Socket Client.
 *
 * @author DavideMammarella
 */
public interface ClientController {
    // metodi invocati dal server ogni volta che l'azione richiesta dal player viene accettata
    // metodi che si scatenano quando il server autorizza un player
}