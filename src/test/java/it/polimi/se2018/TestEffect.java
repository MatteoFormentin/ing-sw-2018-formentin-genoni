package it.polimi.se2018;

public class TestEffect {

    /*
  //  private static final RestrictionCellValueViolatedException valueCellR = new RestrictionCellValueViolatedException();
    private WindowPatternCard testWindowPatternCard;

    private Dice dice;
    private TestFactory factoryDice;
    private GameBoard game;
    private ServerController fakeServer;
    private EventClient eventClientTest;
    private int[] monoInfo;
    private int[] doubleInfo;

    @Before
    public void initTestWindowPatternCard() {
        Cell[][] matrix;
        matrix = new Cell[4][5];
        for(int i=0; i<matrix[0].length;i++){
            matrix[0][i] = new Cell();
            matrix[1][i] = new Cell();
            matrix[1][i].setColorRestriction(DiceColor.getDiceColor(i));
            matrix[2][i] = new Cell();
            matrix[2][i].setValueRestriction(i+1);
            matrix[3][i] = new Cell();
        }

        fakeServer = new ServerController() {
            @Override
            public void login(RemotePlayer remotePlayer){

            }

            @Override
            public void sendEventToController(EventServer eventController) {

            }

            @Override
            public void startGame() {

            }

            @Override
            public void playerDisconnect(RemotePlayer player) {

            }

            @Override
            public void ping(){

            }

            /*@Override
            public void joinGame(RemotePlayer remotePlayer) {

            }*/

    /*        @Override
            public void sendEventToView(EventClient eventClient) {
                eventClientTest = eventClient;
            }

            @Override
            public RemotePlayer searchPlayerById(int id) {
                return null;
            }
        };
        testWindowPatternCard = new WindowPatternCard("test", 5, matrix);
        factoryDice= new TestFactory();
        //game= new GameBoard(1,fakeServer);
        monoInfo= new int[1];
        doubleInfo= new int[2];
    }
/*
        assertThrows(adjacentR.getClass(), () ->);
*/

    /*@Test
    public void testEffectRight() throws GameException {
        EffectGame effectGame = new InsertDice(true,true,true,true);
        assertEquals(SelectCellOfWindow.class,effectGame.eventViewToAsk().getClass());
    }

    @Test
    public void testBoolInsertDice() throws WindowRestriction {

    }*/
}