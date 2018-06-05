package it.polimi.se2018.network.server;

import it.polimi.se2018.network.RemotePlayer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to manage pre-game.
 * This class define a single thread.
 *
 * @author DavideMammarella
 */
public class PreGameThread implements Runnable {

    // Thread che verrà utilizzato
    private Thread preGameThread;

    // Interfaccia server, serve per comunicare col server
    private ServerController server;

    // player attuale, serve per controllare se è già nel game o meno
    private RemotePlayer remotePlayer;

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Pre-game thread constructor.
     *
     * @param server server interface, used as controller to communicate with the server.
     */
    public PreGameThread(ServerController server, RemotePlayer remotePlayer) {
        this.server = server;
        this.remotePlayer=remotePlayer;
    }

    //------------------------------------------------------------------------------------------------------------------
    // RUNNER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for pre-game thread.
     * While loop use AtomicBoolean in order to permit a clean managing of the thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (running.get()) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted...");
                }

                if(!remotePlayer.getPlayerRunning()){
                    System.out.println("Joining the game...");
                    server.joinGame(remotePlayer);
                    shutdown();
                } else {
                    System.out.println("Starting the game...");
                    server.startGame();
                    shutdown();
                }
            shutdown();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the pre-game thread.
     */
    public void startThread(){
        preGameThread=new Thread(this);
        running.set(true);
        preGameThread.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STOPPER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Stopper for the pre-game thread.
     */
    public void shutdown(){
        running.set(false);
    }

    //------------------------------------------------------------------------------------------------------------------
    // SUPPORTER METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter for the pre-game thread status.
     *
     * @return true if thread is running, false otherwise.
     */
    public boolean getThreadStatus(){
        return this.running.get();
    }


}
