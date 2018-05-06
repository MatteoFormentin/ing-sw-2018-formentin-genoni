package it.polimi.se2018.model;


import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.dice.FactoryBalancedDice;
import it.polimi.se2018.model.dice.DiceStack;
import it.polimi.se2018.model.dice.FactoryRandomDice;

import java.util.LinkedList;

import static it.polimi.se2018.model.dice.FactoryBalancedDice.getBalancedDiceFactory;


/**
 * <strong>GameBoard</strong> singleton Class only one game at a time
 * @author Luca Genoni
 * @version 1.2 fix getDice() & getpoll removed
 * @since 1.0
 */
public class GameBoard {
    private int currentround;
    private DiceStack[] roundTrack;
    private Player[] player;
    private ToolCard[] toolCard;
    private ObjectivePublicCard[] objectivePublicCard;
    private DiceStack poolDice; // can also be a DiceStack
    private Player currentPlayer;

    /**
     * /**
     * Method <strong>GameBoard</strong>
     * <em>Description</em>: constructor for the gameBoard. The preparation of the game
     */
    private GameBoard() {
        currentround =0;
        roundTrack = new DiceStack[10];
        toolCard = new ToolCard[3];
        objectivePublicCard = new ObjectivePublicCard[3];

    }
    /**
     * Method <strong>doGame</strong>
     * <em>Description</em>: Setup,start and end of the game,
     *
     * @param roomPlayers a LinkedList of player
     */
    public void doGame(LinkedList<Player> roomPlayers, int indexFirstPlayer,boolean typeOfFactory ){
        //init game of card arrays & FactoryDice
        if(typeOfFactory)poolDice.setDiceFactory(getBalancedDiceFactory());
        else poolDice.setDiceFactory(new FactoryRandomDice());
        Deck deck = Deck.getDeck();
        for(int i=0;i<toolCard.length;i++) toolCard[i] = deck.drawToolCard();
        for(int i=0;i<objectivePublicCard.length;i++) objectivePublicCard[i] = deck.drawObjectivePublicCard();
        //init player
        player = new Player[roomPlayers.size()];
        for(int i=0;i<roomPlayers.size();i++) {
            player[i] = roomPlayers.get(i);
            if(player[i].getPrivateObject()==null){
                player[i].setId(i);
                player[i].setPrivateObject(deck.drawObjectivePrivateCard());
            }
        }
        // there is the need to implement the uml for to decide which window to choose
        /*for(int i=0;i<player.length;i++) {
            playerMove.setupwindowPattern
            WindowPatternCard[]windowPatternCards
            setwindowWindowPatternCard[]windowPatternCards
            favorToken=0;
        }*/

        currentPlayer=player[indexFirstPlayer];
        //now can proceed with the real game
        for(int i=currentround;i<10;i++){
            doRound();
        }
        //clean up game
        if(typeOfFactory) FactoryBalancedDice.reset();
    }
/*
    /**
     * IDEA of a Method for load a stored game
     * @param Players LinkedList
     * @param indexCurrentPlayer int
     * @param roundTrack array of DiceStack
     * @param currentNumberOfEachDiceInFactory array of int
     * @param toolCard array of toolCard
     * @param objectivePublicCard array of objectivePublicCard
     *//*
    public void LoadGame(LinkedList<Player> Players, int indexCurrentPlayer, int[] currentNumberOfEachDiceInFactory,
                         DiceStack[] roundTrack, int currentround, DiceStack poolDice,
                         ToolCard[] toolCard, ObjectivePublicCard[] objectivePublicCard){
        //init card & dice
        FactoryBalancedDice diceBalancedFactory = getBalancedDiceFactory();
        diceBalancedFactory.setcurrentNumberOfEachDice(currentNumberOfEachDiceInFactory);
        this.roundTrack=roundTrack;
        this.currentround=currentround;
        this.poolDice=poolDice;
        this.toolCard=toolCard;
        this.objectivePublicCard=objectivePublicCard;
        //common start
        doGame(Players,indexCurrentPlayer);
    }*/
    private void doRound(){
        //init round
        poolDice= new DiceStack(player.length*2+1);
        for(int i=0;i<player.length;i++){
            if(!currentPlayer.isSecondTurn()) doTurn();
            nextPlayer(true);
        }//now currentPLayer is the first player who played!
        for(int i=0;i<player.length;i++){
            nextPlayer(false);
            if(currentPlayer.isSecondTurn()) doTurn();
        }//now currentPLayer is the last player who played!
        //clean round
        nextPlayer(true);
        roundTrack[currentround]=poolDice;
        currentround++;
    }
    /**
     * /**
     * Method private <strong>doRound</strong>
     * <em>Description</em>: return the next player
     *
     * @param clockWise true if the rotation is clockwise, otherwise false
     */
    private void nextPlayer(Boolean clockWise){
        if(clockWise){
            int indexCurrentPlayer=currentPlayer.getId()+1;
            if(indexCurrentPlayer==player.length) currentPlayer=player[0];// or currentPlayer=player[currentPlayer-player.length];
        }else {
            int indexCurrentPlayer=currentPlayer.getId()-1;
            if(indexCurrentPlayer<0) currentPlayer=player[player.length-1];
        }
    }
    private void doTurn(){


        //finish turn
        currentPlayer.changeSecondTurn();
    }

    public void insertDice(){

    }
    public void useToolCard() {

    }

    public void endTurn() {

    }



}
