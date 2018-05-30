/*package it.polimi.se2018.network.server;

public class Timer implements Runnable {

    long timeout;
    ServerController server;

    public Timer(long timeout, ServerController server) {
        this.timeout = timeout;
        this.server = server;
    }

    @Override
    public void run() {
        boolean flag = true;
        long startTimerTime = System.currentTimeMillis();
        while (flag) {
            if (System.currentTimeMillis() - startTimerTime >= timeout) {
                flag = false;
            }
        }
        server.startGame();

    }
}*/
