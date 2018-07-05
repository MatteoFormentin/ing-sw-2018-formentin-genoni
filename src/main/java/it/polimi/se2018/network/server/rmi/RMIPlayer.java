package it.polimi.se2018.network.server.rmi;


import it.polimi.se2018.list_event.event_received_by_view.EventClient;
import it.polimi.se2018.network.RemotePlayer;
import it.polimi.se2018.network.client.rmi.IRMIClient;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class that extends the RemotePlayer in order to define an RMI Player.
 *
 * @author DavideMammarella
 */
public class RMIPlayer extends RemotePlayer {

    // CLIENT INTERFACE
    private transient IRMIClient iRMIClient;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * RMI Player constructor.
     *
     * @param iRMIClient client associated to the player.
     */
    public RMIPlayer(IRMIClient iRMIClient) {
        // TODO FORSE RIMUOVI PLAYERRUNNING
        playerRunning = true;
        playerConnection="rmi";
        this.iRMIClient = iRMIClient;
    }

    //------------------------------------------------------------------------------------------------------------------
    // METHOD CALLED FROM SERVER - REQUEST TO THE CLIENT
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Remote method used to send to the client an update of the game.
     *
     * @param eventClient object that will use the client to unleash the update associated.
     */
    @Override
    public void sendEventToView(EventClient eventClient) throws RemoteException{
        iRMIClient.sendEventToView(eventClient);
    }

    /**
     * Remote method used to ping the client.
     * If the remote player that will call the ping will not found the disconnection of the player will be called.
     */
    @Override
    public void ping(){
            try{
                this.iRMIClient.ping();
                playerRunning=true;
            } catch (RemoteException e){
                System.err.println("Player: "+getNickname()+" has a broken connection.\nTrying to delete the connection from the server...");
                disconnect();
            }
    }

    /**
     * Method used to to send an ACK (Acknowledge) packet from server to client in order to signal
     * the correct reception of a data packet. (SOCKET)
     */
    @Override
    public void sendAck() { }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the interface of the RMI Client.
     *
     * @return client associated to the player.
     */
    public IRMIClient getiRMIClient() {
        return iRMIClient;
    }

    /**
     * Method used to remove a player from the RMI server.
     * This method will also set the playerRunning boolean to false in order to remove correctly the user.
     */
    @Override
    public void disconnect(){
        //Server.removeRMIPlayer(this);
        playerRunning=false;
        try {
            UnicastRemoteObject.unexportObject(iRMIClient, true);
        }catch(NoSuchObjectException ex){
            System.err.println("No reference to the remote object.");
        }
        AnsiConsole.out.println(ansi().fg(GREEN).a(getNickname()+" has been removed!").reset());
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("-----------------------------------------").reset());
    }



}
