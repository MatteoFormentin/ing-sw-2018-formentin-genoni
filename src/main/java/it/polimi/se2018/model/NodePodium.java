package it.polimi.se2018.model;

import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;

public class NodePodium {
    //node
    private NodePodium father;
    private NodePodium leftLessPoint;
    private NodePodium rightMorePoint;

    //for calculate points
    private Player player;
    ObjectivePublicCard[] publicCard;

    //to update the info
    private int pointsPrivate;
    private int[] pointsPublic;
    private int tokenLeft;
    private int voidCell;
    private int totalPoints;

    //for print out
    private String[] description;
    private String privateColor;

    public NodePodium(Player player, ObjectivePublicCard[] publicCard) {
        //5 info fixed + n variables
        this.player = player;
        this.publicCard = publicCard;
        int numberCard;
        if(publicCard==null) numberCard=0;
        else numberCard = publicCard.length;
        description = new String[5 + numberCard];
        pointsPublic = new int[numberCard];
        int i = 0;
        description[i++] = "Giocatore";
        description[i++] = "Obiettivo Privato";
        for (int j = 0; j < numberCard; j++) description[i++] = publicCard[j].getName();
        description[i++] = "Token Rimasti";
        description[i] = "Celle vuote";
        privateColor = player.getPrivateObject().getDiceColor().toString();

    }

    public void calculatePoint() {
        totalPoints=0;
        WindowPatternCard window = player.getPlayerWindowPattern();
        int numberCell = window.getMatrix().length;
        if (numberCell != 0) numberCell = window.getColumn(0).length * numberCell;
        pointsPrivate = player.getPrivateObject().calculatePoint(window);
        for (int j = 0; j < pointsPublic.length; j++) pointsPublic[j] = publicCard[j].calculatePoint(window);
        tokenLeft = player.getFavorToken();
        voidCell = numberCell - window.getNumberOfCellWithDice();
        for (int aPointsPublic : pointsPublic) totalPoints += aPointsPublic;
        totalPoints = pointsPrivate + tokenLeft + voidCell;
    }


    public int getPointsPrivate() {
        return pointsPrivate;
    }
    public int getIndexPlayer(){
        return player.getIndexInGame();
    }

    public int getTokenLeft() {
        return tokenLeft;
    }


    public int getTotalPoints() {
        return totalPoints;
    }

    public void setFather(NodePodium father) {
        this.father = father;
    }

    public void setLeftLessPoint(NodePodium leftLessPoint) {
        this.leftLessPoint = leftLessPoint;
    }

    public void setRightMorePoint(NodePodium rightMorePoint) {
        this.rightMorePoint = rightMorePoint;
    }

    public NodePodium getFather() {
        return father;
    }

    public NodePodium getLeftLessPoint() {
        return leftLessPoint;
    }

    public NodePodium getRightMorePoint() {
        return rightMorePoint;
    }

    /*********************************************************************************************/
    public String[] getDescription(){
        return description;
    }
    public int[] getArrayIntInfo(){
        int[] info = new int[4+publicCard.length];
        int i=0;
        info[i++] = player.getIndexInGame();
        info[i++] = pointsPrivate;
        for (int j = 0; j < publicCard.length; j++) info[i++] = pointsPublic[j];
        info[i++] = tokenLeft;
        info[i++] = voidCell;
        info[i] = totalPoints;
        return info;
    }

    public String getPrivateColor() {
        return privateColor;
    }
}