package it.polimi.se2018.model;

public interface UpdateRequestedByServer {
    //TODO modifica questa interfaccia come vuoi, ti permette di inviare pacchetti senza duplicare codice
    public void updateInfoReLogin(int indexPlayer,boolean duringSetUp);
    public void updateInfoStart();
    public void nameConfirmedInInTheGame(String[] name);
    public void updatePlayerConnected(int index,String name);
    public void updateDisconnected(int index,String name);
}
