package it.polimi.se2018.network.server;

import it.polimi.se2018.network.RemotePlayer;

/**
 * Interface based on the Abstract Factory Design Pattern.
 * This interface is used as server controller in AbstractServer.
 * Implemented by Server class in order to provide basic methods for RMI and Socket Server.
 *
 * @author DavideMammarella
 */
public interface ServerController {

    /**
     * Log the user to the Server with the username.
     *  @param username name used for the player.
     * @param remotePlayer reference to RMI or Socket Player
     */
    // manca exception
    String login (String username, RemotePlayer remotePlayer);

    /**
     * Getter for RemotePlayer.
     *
     * @param username name used for the player.
     * @return RemotePlayer associated to the username.
     */
    RemotePlayer getPlayer(String username);

    // GAME CREATOR

}

