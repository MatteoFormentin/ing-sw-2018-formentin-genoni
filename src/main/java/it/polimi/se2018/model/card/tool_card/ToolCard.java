package it.polimi.se2018.model.card.tool_card;

import it.polimi.se2018.model.GameBoard;

/**
 * Base class for tool card.
 * Card id starts form 0 instead of 1.
 *
 * @author Matteo Formentin
 */
public abstract class ToolCard {
    private int id;
    private String name;
    private String description;
    private int favorToken=1; //Punti necessari all'uso, aumentano solo dopo il primo utilizzo


    /**
     * Return card id.
     *
     * @return id int.
     */
    public int getId() {
        return id;
    }

    /**
     * Set card id.
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return card name.
     *
     * @return name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Set card name.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return card description.
     *
     * @return description String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set card description.
     *
     * @param description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return card Favor Token.
     *
     * @return Point int.
     */
    public int getFavorToken() {
        return favorToken;
    }

    /**
     * Increment by one card Favor Token.
     * Call after first use.
     */
    public void incrementFavorToken() {
        favorToken = 2;
    }

    /**
     * check the tool without Pre-Condition
     *
     * @author Luca Genoni
     * @param gameBoard the gameboard that store the data
     * @param idPlayer the id of the player who want use the card
     * @return true if the player can use the card
     */
    protected boolean noPreCondition(GameBoard gameBoard, int idPlayer){
        if(gameBoard.getPlayer(idPlayer).isHasUsedToolCard()) return false; //tool card already used
        if(gameBoard.getPlayer(idPlayer).getFavorToken()<this.getFavorToken()) return false; // not enough token
        return true;
    }

    /**
     * check the tool with Pre-Condition of the Dice in hand
     *
     * @author Luca Genoni
     * @param gameBoard the gameboard that store the data
     * @param idPlayer the id of the player who want use the card
     * @return true if the player can use the card
     */
    protected boolean preConditionOfDicePool(GameBoard gameBoard, int idPlayer){
        //need to activate the tool
        if(gameBoard.getPlayer(idPlayer).isHasUsedToolCard()) return false; //tool card already used
        if(gameBoard.getPlayer(idPlayer).getFavorToken()<this.getFavorToken()) return false; // not enough token
        if (!gameBoard.getPlayer(idPlayer).isHasDrawNewDice()) return false; // don't have the a Dice in hand
        if (gameBoard.getPlayer(idPlayer).isHasPlaceANewDice()) return false; //dice already placed
        //the tool can be used

        return true;
    }
    /**
     * update the data relative to the use of the Tool Card
     *
     * @author Luca Genoni
     * @param gameBoard the gameboard that store the data
     * @param idPlayer the id of the player who want use the card
     * @param indexOfCardInGame index of the Card in the Game(0,1,2 only 3 card)
     */
    protected void saveUsed(GameBoard gameBoard, int idPlayer, int indexOfCardInGame){
        gameBoard.getPlayer(idPlayer).useFavorToken(this.getFavorToken());
        gameBoard.getToolCard(indexOfCardInGame).incrementFavorToken();
        gameBoard.getPlayer(idPlayer).setHasUsedToolCard(true);
    }
}
