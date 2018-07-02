package it.polimi.se2018.model;

class TreePodium {
    private NodePodium root;
    private int[] orderWinTies;
    private int numberPlayer;
    private int numberRound;
    /**
     * Costruttore necessita sapere il numero totale dei giocatori e il numero dei round
     * per poter determinare l'ordine di vittoria in caso di assoluta parità
     *
     * @param numberPlayer numero dei giocatori che verranno inseriti nel podio
     * @param numberRound numero dei round della partita
     */
    TreePodium(int numberPlayer, int numberRound) {
        root = null;
        this.numberPlayer=numberPlayer;
        this.numberRound=numberRound;
    }

    public NodePodium getRoot() {
        return root;
    }

    private void buildLastRound(){
        if(orderWinTies==null){
            if(numberRound<0) return;
            orderWinTies = new int[numberPlayer];
            int lastPlayer = (numberRound-1) % numberPlayer;
            int counter;
            for (int i=0;i< numberPlayer;i++){
                counter = (i+ lastPlayer) % numberPlayer;
                orderWinTies[counter] = numberPlayer -i;
            }
        }
    }

    /**
     * Appena viene inserito il nuovo nodo vengono calcolati i punti e viene inserito in modo ordinato
     * all'interno dell'albero binario, seguendo la seguente logica:
     * Se i giocatori con più punti vanno nel ramo di destra, chi ne ha meno nel ramo di sinistra
     * Nel caso di parità verranno controllati in ordine i seguenti parametri del nodo:
     * 1 punti accumulati con l'obbiettivo privato
     * 2 Token non utilizzati
     * 3 Ordine a ritroso dell'ultimo round. (Vince quello che ha giocato per ultimo tra i due)
     *
     * @param newNodePlayer nuovo nodo da inserire, vengono ricalcolati i punti
     */
    void insertNodePlayer(NodePodium newNodePlayer) {
        NodePodium y = null;
        NodePodium x = root;
        newNodePlayer.calculatePoint();
        while (x != null) {
            y = x;
            if (newNodePlayer.getTotalPoints() < x.getTotalPoints()) x = x.getLeftLessPoint();
            else if (newNodePlayer.getTotalPoints() > x.getTotalPoints()) x = x.getRightMorePoint();
            else {//first type of ties check the point accumulated with the private points
                if (newNodePlayer.getPointsPrivate() < x.getPointsPrivate()) x = x.getLeftLessPoint();
                else if (newNodePlayer.getPointsPrivate() > x.getPointsPrivate()) x = x.getRightMorePoint();
                else {//second type of ties check the token left
                    if (newNodePlayer.getTokenLeft() < x.getTokenLeft()) x = x.getLeftLessPoint();
                    else if (newNodePlayer.getTokenLeft() > x.getTokenLeft()) x = x.getRightMorePoint();
                    else {//third type of ties check the reverse order
                        buildLastRound();
                        if (orderWinTies[newNodePlayer.getIndexPlayer()] > orderWinTies[x.getIndexPlayer()])
                            x = x.getLeftLessPoint();
                        else x = x.getRightMorePoint();
                    }
                }

            }
        }
        newNodePlayer.setFather(y);
        if (y == null) this.root = newNodePlayer;
        else if (newNodePlayer.getTotalPoints() < y.getTotalPoints()) y.setLeftLessPoint(newNodePlayer);
        else if (newNodePlayer.getTotalPoints() > y.getTotalPoints()) y.setRightMorePoint(newNodePlayer);
        else {//first type of ties check the point accumulated with the private points
            if (newNodePlayer.getPointsPrivate() < y.getPointsPrivate()) y.setLeftLessPoint(newNodePlayer);
            else if (newNodePlayer.getPointsPrivate() > y.getPointsPrivate()) y.setRightMorePoint(newNodePlayer);
            else {//second type of ties check the token left
                if (newNodePlayer.getTokenLeft() < y.getTokenLeft()) y.setLeftLessPoint(newNodePlayer);
                else if (newNodePlayer.getTokenLeft() > y.getTokenLeft()) y.setRightMorePoint(newNodePlayer);
                else {//third type of ties check the reverse order
                    if (orderWinTies[newNodePlayer.getIndexPlayer()] > orderWinTies[y.getIndexPlayer()])
                        y.setLeftLessPoint(newNodePlayer);
                    else y.setRightMorePoint(newNodePlayer);
                }
            }

        }
    }

    private NodePodium max(NodePodium node) {
        while (node.getRightMorePoint() != null) node = node.getRightMorePoint();
        return node;
    }

    private NodePodium predecessor(NodePodium nodePlayer) {
        if (nodePlayer.getLeftLessPoint() != null) return max(nodePlayer.getLeftLessPoint());
        if(nodePlayer.getFather()!=null){
            NodePodium y = nodePlayer.getFather();
            if (y.getRightMorePoint() == nodePlayer) return y;
        }
        return null;
    }

    int[][] getSortedPlayer() {
        if(root!=null){
            NodePodium currentPlayerSorted = this.max(root);
            int numberInfo = currentPlayerSorted.getArrayIntInfo().length;
            int[][] sortedPlayer = new int[numberPlayer][numberInfo];
            int i = 0;
            while (i < numberPlayer &&currentPlayerSorted!= null) {
                sortedPlayer[i] = currentPlayerSorted.getArrayIntInfo();
                currentPlayerSorted = this.predecessor(currentPlayerSorted);
                i++;
            }
            return sortedPlayer;
        }
        return new int[0][0];
    }


}