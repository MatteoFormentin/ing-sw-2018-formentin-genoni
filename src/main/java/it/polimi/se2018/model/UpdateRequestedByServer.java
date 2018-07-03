package it.polimi.se2018.model;

public interface UpdateRequestedByServer {
    //TODO modifica questa interfaccia come vuoi, ti permette di inviare pacchetti senza duplicare codice

    //metodi del server
    void updatePlayerConnected(int index, String name);

    void updateDisconnected(int index, String name);


    // metodi da checkare
    void updateInfoReLogin(int indexPlayer);

    void updateInfoStart();
}
