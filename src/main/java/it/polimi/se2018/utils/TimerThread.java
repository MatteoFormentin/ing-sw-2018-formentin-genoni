package it.polimi.se2018.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simple timeout with callback.
 * <p>
 * Since java doesn't provide callback, caller class must implement TimerCallback Interface.
 * This class define a single thread.
 *
 * @author Matteo Formentin
 * @author DavideMammarella
 */
public class TimerThread implements Runnable {

    // Utilizzo variabili atomiche perchè evitano problemi di concorrenza
    // Così prevengo conflitti nel settaggio e check delle variabili da metodi differenti
    private final AtomicBoolean running = new AtomicBoolean();
    // Thread che verrà utilizzato
    private Thread timerThread;
    // Interfaccia server, serve per comunicare col server
    private TimerCallback timerCallback;
    //Timeout passato da server (caricato da file)
    private long timeout;
    private long startTimerTime;

    //------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Timer Constructor.
     *
     * @param timerCallback server interface, used as controller to communicate with the server.
     * @param timeout       timeout used to impose a time limit on login before starting a game.
     */
    public TimerThread(TimerCallback timerCallback, long timeout) {
        this.timeout = timeout;
        this.timerCallback = timerCallback;
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
        // Punto di inizio del conteggio per il timer
        startTimerTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTimerTime <= timeout) {
            if (!running.get()) return;
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted...");
            }
        }
        timerCallback.timerCallback();
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STARTER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Starter for the Timer Thread.
     */
    public void startThread() {
        timerThread = new Thread(this);
        running.set(true);
        timerThread.start();
    }

    //------------------------------------------------------------------------------------------------------------------
    // THREAD STOPPER
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Stopper for the Timer Thread.
     */
    public void shutdown() {
        running.set(false);
    }

    /**
     * Reset the Timer.
     */
    public void resetTimer() {
        startTimerTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return timerThread.isAlive();
    }
}
