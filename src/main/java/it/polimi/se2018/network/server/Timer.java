package it.polimi.se2018.network.server;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to manage time on login.
 * This class define a single thread.
 *
 * @author DavideMammarella
 */
public class Timer implements Runnable {

    // Thread che verrà utilizzato
    private Thread timerThread;

    // Interfaccia server, serve per comunicare col server
    private ServerController server;

    //Timeout passato da server (caricato da file)
    private long timeout;
    // Punto di inizio del conteggio per il timer
    private long startTimerTime;

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Timer Constructor.
     *
     * @param server server interface, used as controller to communicate with the server.
     * @param timeout timeout used to impose a time limit on login before starting a game.
     */
    public Timer(ServerController server, long timeout) {
        this.timeout = timeout;
        this.server = server;
    }

    //------------------------------------------------------------------------------------------------------------------
    // RUNNER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Runner for Timer Thread.
     * While loop use AtomicBoolean in order to permit a clean managing of the thread.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        // Inizio conteggio timer
        startTimerTime = System.currentTimeMillis();

        while (running.get()) {
            while(System.currentTimeMillis() - startTimerTime <= timeout){
                try {
                    Thread.sleep(this.timeout);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted...");
                }
            }

            System.out.println("Time is over, starting the game...");
            server.startGame();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the Timer Thread.
     */
    public void startThread(){
        timerThread=new Thread(this);
        running.set(true);
        timerThread.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STOPPER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Stopper for the Timer Thread.
     */
    public void shutdown(){
        running.set(false);
    }


}
