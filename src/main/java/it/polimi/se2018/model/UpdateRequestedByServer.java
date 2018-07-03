package it.polimi.se2018.model;

public interface UpdateRequestedByServer {
    //TODO modifica questa interfaccia come vuoi, ti permette di inviare pacchetti senza duplicare codice

    //metodi del server
    public void updatePlayerConnected(int index,String name);
    public void updateDisconnected(int index,String name);



    // metodi da checkare
    public void updateInfoReLogin(int indexPlayer);
    public void updateInfoStart();
}
