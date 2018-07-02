package it.polimi.se2018.model;

import it.polimi.se2018.alternative_network.newserver.GameRoom;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.network.server.ServerController;

public class UpdaterView {
    private GameBoard gameBoard;
    private ServerController server;
    private GameRoom server2;

    public UpdaterView(GameBoard gameBoard, ServerController server, GameRoom server2) {
        this.gameBoard = gameBoard;
        this.server = server;
        this.server2 = server2;
    }

    /************************************ CAN SEND TO ALL ************************************************/
    public void updateAllPublicObject() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++)  updateAllPublicObject(i);
    }

    public void updateAllPublicObject(int indexPlayerToNotify) {
        UpdateAllPublicObject packet = new UpdateAllPublicObject(gameBoard.getObjectivePublicCard());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }


    /************************************ CAN SEND TO ALL ************************************************/
    public void updateAllToolCard() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateAllToolCard(i);
    }

    /**
     * the tool card to the player
     */
    public void updateAllToolCard(int indexPlayerToNotify) {
        UpdateAllToolCard packet = new UpdateAllToolCard(gameBoard.getToolCard());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    /************************************ CAN SEND TO ALL ************************************************/

    public void updateInitDimRound(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInitDimRound(i);
    }

    public void updateInitDimRound(int indexPlayerToNotify){
        UpdateInitDimRound packet = new UpdateInitDimRound(gameBoard.getRoundTrack());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    /************************************ FOR SINGLE PLAYER *****************************************/
    public void updateInitialWindowPatternCard(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInitialWindowPatternCard(i);
    }

    public void updateInitialWindowPatternCard(int indexPlayerToNotify){
        UpdateInitialWindowPatternCard packet = new UpdateInitialWindowPatternCard(gameBoard.getPlayer(indexPlayerToNotify).getThe4WindowPattern());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    public void updateSinglePrivateObject() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateSinglePrivateObject(i);
    }

    public void updateSinglePrivateObject(int indexPlayerToNotify) {
        UpdateSinglePrivateObject packet = new UpdateSinglePrivateObject(indexPlayerToNotify, gameBoard.getPlayer(indexPlayerToNotify).getPrivateObject());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }


    public void updateDicePool(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateDicePool(i);
    }

    public void updateDicePool(int indexPlayerToNotify){
        UpdateDicePool packet = new UpdateDicePool(gameBoard.getDicePool());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }


    public void updateInfoCurrentTurn(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInfoCurrentTurn(i);
    }

    public void updateInfoCurrentTurn(int indexPlayerToNotify){
        UpdateInfoCurrentTurn packet = new UpdateInfoCurrentTurn(gameBoard.getCurrentRound(),gameBoard.getCurrentTurn());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    /**
     * Send to all the change for the cell changed
     *
     * @param indexPlayerThatChanged the player that changed his window
     * @param rowCell the row of the cell in the window
     * @param columnCell the line of the cell in the window
     */
    public void updateSingleCell(int indexPlayerThatChanged,int rowCell,int columnCell) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateSingleCell(i,indexPlayerThatChanged,rowCell,columnCell);
    }

    public void updateSingleCell(int indexPlayerToNotify,int indexPlayerThatChanged,int rowCell,int columnCell) {
        Cell cellModified = gameBoard.getPlayer(indexPlayerThatChanged).getPlayerWindowPattern().getCell(rowCell,columnCell);
        UpdateSingleCell packet = new UpdateSingleCell(indexPlayerThatChanged,rowCell,columnCell,
                cellModified.getDice(),cellModified.getValueRestriction(),cellModified.getColorRestriction());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }


    public void updatePlayerHand(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerHand(i);
    }

    public void updatePlayerHand(int indexPlayerThatChanged){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerHand(i,indexPlayerThatChanged);
    }

    /**
     * send the token of the player update to a single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param indexPlayerThatChanged the dice in hand
     */
    public void updatePlayerHand(int indexPlayerToNotify,int indexPlayerThatChanged) {
        UpdateSinglePlayerHand packet = new UpdateSinglePlayerHand(indexPlayerThatChanged,gameBoard.getPlayer(indexPlayerThatChanged).getHandDice());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    public void updatePlayerTokenAndPoints(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerTokenAndPoints(i);
    }
    /**
     * send the token of the player update to all players
     *
     * @param indexPlayerThatChanged the token of the player that changed
     */
    public void updatePlayerTokenAndPoints(int indexPlayerThatChanged){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerTokenAndPoints(i,indexPlayerThatChanged);
    }

    /**
     * send the token of the player update to a single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param indexPlayerThatChanged the token of the player that changed
     */
    public void updatePlayerTokenAndPoints(int indexPlayerToNotify,int indexPlayerThatChanged){
        Player playerChanged = gameBoard.getPlayer(indexPlayerThatChanged);
        UpdateSinglePlayerTokenAndPoints packet = new UpdateSinglePlayerTokenAndPoints(
                indexPlayerThatChanged,playerChanged.getFavorToken(),playerChanged.getPoints());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    public void updateToolCardCost(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateToolCardCost(i);
    }
    /**
     * send the cost of the tool card update to all players
     *
     * @param indexToolCardChanged the tool card that changed the cost
     */
    public void updateToolCardCost(int indexToolCardChanged){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateToolCardCost(i,indexToolCardChanged);
    }

    /**
     * send the cost of the tool card update to a single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param indexToolCardChanged the tool card that changed the cost
     */
    public void updateToolCardCost(int indexPlayerToNotify,int indexToolCardChanged){
        ToolCard toolCard = gameBoard.getToolCard(indexToolCardChanged);
        UpdateSingleToolCardCost packet = new UpdateSingleToolCardCost(indexToolCardChanged,toolCard.getFavorToken());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

    public void updateSingleTurnRoundTrack(int indexRoundChanged){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateToolCardCost(i,indexRoundChanged);
    }

    public void updateSingleTurnRoundTrack(int indexPlayerToNotify,int indexRoundChanged){
        UpdateSingleTurnRoundTrack packet = new UpdateSingleTurnRoundTrack(indexRoundChanged,gameBoard.getRoundTrack(indexRoundChanged));
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }
    /**
     * send all the windows update to all players
     */
    public void updateWindow(){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateWindow(i);
    }

    /**
     * send the window update to all players
     *
     * @param indexPlayerChanged the player of the window updated
     */
    public void updateWindow(int indexPlayerChanged){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateWindow(i,indexPlayerChanged);
    }

    /**
     * send the window update to a single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param indexPlayerChanged the player of the window updated
     */
    public void updateWindow(int indexPlayerToNotify, int indexPlayerChanged){
        Player player = gameBoard.getPlayer(indexPlayerChanged);
        UpdateSingleWindow packet = new UpdateSingleWindow(indexPlayerChanged,player.getPlayerWindowPattern());
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }
/*
    public void updatePointsDuringGame(int indexPlayerToNotify,int [][] sortedPlayer,String[] description){
        //TODO aggiungere un evento per aggiornare i punti correnti
    }*/

    /**
     * send the podium update to all the players
     *
     * @param sortedPlayer the 2D array that contains the info of the podium (first index position, second index the info)
     * @param description the description of the info related to the 2D array
     */
    public void updateStatPodium(int [][] sortedPlayer,String[] description){
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateStatPodium(i,sortedPlayer,description);
    }

    /**
     * send the podium update to single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param sortedPlayer the 2D array that contains the info of the podium (first index position, second index the info)
     * @param description the description of the info related to the 2D array
     */
    public void updateStatPodium(int indexPlayerToNotify,int [][] sortedPlayer,String[] description){
        UpdateStatPodium packet = new UpdateStatPodium(sortedPlayer,description);
        packet.setPlayerId(indexPlayerToNotify);
        if (server == null) server2.sendEventToView(packet);
        else server.sendEventToView(packet);
    }

}
