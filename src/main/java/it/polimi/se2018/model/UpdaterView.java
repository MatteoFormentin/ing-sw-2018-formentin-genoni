package it.polimi.se2018.model;

import it.polimi.se2018.alternative_network.newserver.room.GameInterface;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.*;
import it.polimi.se2018.list_event.event_received_by_view.event_from_model.setup.*;
import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;

public class UpdaterView implements UpdateRequestedByServer {
    private GameBoard gameBoard;
    private GameInterface server2;


    public UpdaterView(GameBoard gameBoard, GameInterface server2) {
        this.gameBoard = gameBoard;
        this.server2 = server2;
    }

    /************************************ CAN SEND TO ALL ************************************************/
    private void updateAllPublicObject() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateAllPublicObject(i);
    }

    private void updateAllPublicObject(int indexPlayerToNotify) {
        UpdateAllPublicObject packet = new UpdateAllPublicObject(gameBoard.getObjectivePublicCard());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    /************************************ CAN SEND TO ALL ************************************************/
    private void updateAllToolCard() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateAllToolCard(i);
    }

    /**
     * the tool card to the player
     */
    private void updateAllToolCard(int indexPlayerToNotify) {
        UpdateAllToolCard packet = new UpdateAllToolCard(gameBoard.getToolCard());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    /************************************ CAN SEND TO ALL ************************************************/

    private void updateInitDimRound() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInitDimRound(i);
    }

    private void updateInitDimRound(int indexPlayerToNotify) {
        UpdateInitDimRound packet = new UpdateInitDimRound(gameBoard.getRoundTrack());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    /************************************ FOR SINGLE PLAYER *****************************************/

    private void updateInitialWindowPatternCard() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInitialWindowPatternCard(i);
    }

    private void updateInitialWindowPatternCard(int indexHolderWindow) {
        updateInitialWindowPatternCard(indexHolderWindow, indexHolderWindow);
    }

    private void updateInitialWindowPatternCard(int indexPlayerToNotify, int indexHolderWindow) {
        UpdateInitialWindowPatternCard packet = new UpdateInitialWindowPatternCard(indexHolderWindow, gameBoard.getPlayer(indexPlayerToNotify).getThe4WindowPattern());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    private void updateSinglePrivateObject() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateSinglePrivateObject(i);
    }

    private void updateSinglePrivateObject(int indexPlayerToNotify) {
        UpdateSinglePrivateObject packet = new UpdateSinglePrivateObject(indexPlayerToNotify, gameBoard.getPlayer(indexPlayerToNotify).getPrivateObject());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    void updateDicePool() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateDicePool(i);
    }

    private void updateDicePool(int indexPlayerToNotify) {
        UpdateDicePool packet = new UpdateDicePool(gameBoard.getDicePool());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    void updateInfoCurrentTurn() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInfoCurrentTurn(i);
    }

    private void updateInfoCurrentTurn(int indexPlayerToNotify) {
        UpdateInfoCurrentTurn packet = new UpdateInfoCurrentTurn(gameBoard.getCurrentRound(), gameBoard.getCurrentTurn());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    /**
     * Send to all the change for the cell changed
     *
     * @param indexPlayerThatChanged the player that changed his window
     * @param rowCell                the row of the cell in the window
     * @param columnCell             the line of the cell in the window
     */
    void updateSingleCell(int indexPlayerThatChanged, int rowCell, int columnCell) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++)
            updateSingleCell(i, indexPlayerThatChanged, rowCell, columnCell);
    }

    private void updateSingleCell(int indexPlayerToNotify, int indexPlayerThatChanged, int rowCell, int columnCell) {
        Cell cellModified = gameBoard.getPlayer(indexPlayerThatChanged).getPlayerWindowPattern().getCell(rowCell, columnCell);
        UpdateSingleCell packet = new UpdateSingleCell(indexPlayerThatChanged, rowCell, columnCell,
                cellModified.getDice(), cellModified.getValueRestriction(), cellModified.getColorRestriction());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    void updatePlayerHand(int indexPlayerThatChanged) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerHand(i, indexPlayerThatChanged);
    }

    /**
     * send the token of the player update to a single player
     *
     * @param indexPlayerToNotify    the player that need the update
     * @param indexPlayerThatChanged the dice in hand
     */
    private void updatePlayerHand(int indexPlayerToNotify, int indexPlayerThatChanged) {
        UpdateSinglePlayerHand packet = new UpdateSinglePlayerHand(indexPlayerThatChanged, gameBoard.getPlayer(indexPlayerThatChanged).getHandDice());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    void updatePlayerTokenAndPoints() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerTokenAndPoints(i);
    }

    /**
     * send the token of the player update to all players
     *
     * @param indexPlayerThatChanged the token of the player that changed
     */
    void updatePlayerTokenAndPoints(int indexPlayerThatChanged) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerTokenAndPoints(i, indexPlayerThatChanged);
    }

    /**
     * send the token of the player update to a single player
     *
     * @param indexPlayerToNotify    the player that need the update
     * @param indexPlayerThatChanged the token of the player that changed
     */
    private void updatePlayerTokenAndPoints(int indexPlayerToNotify, int indexPlayerThatChanged) {
        Player playerChanged = gameBoard.getPlayer(indexPlayerThatChanged);
        UpdateSinglePlayerToken packet = new UpdateSinglePlayerToken(indexPlayerThatChanged, playerChanged.getFavorToken());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    /**
     * send the cost of the tool card update to all players
     *
     * @param indexToolCardChanged the tool card that changed the cost
     */
    void updateToolCardCost(int indexToolCardChanged) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateToolCardCost(i, indexToolCardChanged);
    }

    /**
     * send the cost of the tool card update to a single player
     *
     * @param indexPlayerToNotify  the player that need the update
     * @param indexToolCardChanged the tool card that changed the cost
     */
    private void updateToolCardCost(int indexPlayerToNotify, int indexToolCardChanged) {
        ToolCard toolCard = gameBoard.getToolCard(indexToolCardChanged);
        UpdateSingleToolCardCost packet = new UpdateSingleToolCardCost(indexToolCardChanged, toolCard.getFavorToken());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    void updateSingleTurnRoundTrack(int indexRoundChanged) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateSingleTurnRoundTrack(i, indexRoundChanged);
    }

    private void updateSingleTurnRoundTrack(int indexPlayerToNotify, int indexRoundChanged) {
        UpdateSingleTurnRoundTrack packet = new UpdateSingleTurnRoundTrack(indexRoundChanged, gameBoard.getRoundTrack(indexRoundChanged));
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    /**
     * send the window update to all players
     *
     * @param indexPlayerChanged the player of the window updated
     */
    void updateWindow(int indexPlayerChanged) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateWindow(i, indexPlayerChanged);
    }

    /**
     * send the window update to a single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param indexPlayerChanged  the player of the window updated
     */
    private void updateWindow(int indexPlayerToNotify, int indexPlayerChanged) {
        Player player = gameBoard.getPlayer(indexPlayerChanged);
        UpdateSingleWindow packet = new UpdateSingleWindow(indexPlayerChanged, player.getPlayerWindowPattern());
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    /**
     * send the podium update to all the players
     *
     * @param sortedPlayer the 2D array that contains the info of the podium (first index position, second index the info)
     * @param description  the description of the info related to the 2D array
     */
    void updateStatPodium(int[][] sortedPlayer, String[] description) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateStatPodium(i, sortedPlayer, description);
    }

    /**
     * send the podium update to single player
     *
     * @param indexPlayerToNotify the player that need the update
     * @param sortedPlayer        the 2D array that contains the info of the podium (first index position, second index the info)
     * @param description         the description of the info related to the 2D array
     */
    private void updateStatPodium(int indexPlayerToNotify, int[][] sortedPlayer, String[] description) {
        UpdateStatPodium packet = new UpdateStatPodium(sortedPlayer, description);
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    private void updatePlayerConnected(int indexPlayerToNotify, int index, String name) {
        UpdatePlayerConnection packet = new UpdatePlayerConnection(index, name);
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    private void updateDisconnected(int indexPlayerToNotify, int index, String name) {
        UpdateDisconnectionDuringGame packet = new UpdateDisconnectionDuringGame(index, name);
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }


    private void currentPoints(int indexPlayer) {
        NodePodium currentPoints = gameBoard.calculatePoint(indexPlayer);
        UpdateCurrentPoint packet = new UpdateCurrentPoint(currentPoints.getArrayIntInfo(), currentPoints.getDescription());
        packet.setPlayerId(indexPlayer);
        server2.sendEventToView(packet);
    }

    void currentPoints() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) currentPoints(i);
    }

    private void updateName() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateName(i);
    }

    private void updateName(int indexPlayerToNotify) {
        String[] names = new String[gameBoard.getPlayer().length];
        for (int i = 0; i < gameBoard.getPlayer().length; i++) names[i] = gameBoard.getPlayer(i).getNickname();
        UpdateNamePlayers packet = new UpdateNamePlayers(names);
        packet.setPlayerId(indexPlayerToNotify);
        server2.sendEventToView(packet);
    }

    @Override
    public void updatePlayerConnected(int index, String name) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerConnected(i, index, name);
    }

    @Override
    public void updateDisconnected(int index, String name) {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateDisconnected(i, index, name);
    }

    @Override
    public void updateInfoReLogin(int indexPlayerToNotify) {
        System.out.println();
        System.out.println("Relogin");
        System.out.println();
        updateInfoStart(indexPlayerToNotify);
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateWindow(indexPlayerToNotify, i);
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerHand(indexPlayerToNotify, i);
        updateDicePool(indexPlayerToNotify);
        updateInfoCurrentTurn(indexPlayerToNotify);
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updatePlayerTokenAndPoints();
    }

    @Override
    public void updateInfoStart() {
        for (int i = 0; i < gameBoard.getPlayer().length; i++) updateInfoStart(i);
    }

    public void updateInfoStart(int indexPlayerToNotify) {
        updateName(indexPlayerToNotify);
        updateSinglePrivateObject(indexPlayerToNotify);
        updateInitialWindowPatternCard(indexPlayerToNotify);
        updateAllToolCard(indexPlayerToNotify);
        updateAllPublicObject(indexPlayerToNotify);
        updateInitDimRound(indexPlayerToNotify);
    }
}
