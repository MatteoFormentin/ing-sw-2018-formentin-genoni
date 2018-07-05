package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.card.ToolCard;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.DiceStack;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;


/**
 * Class for Display CLI messages using JANSI LIBRARY.
 *
 * @author Matteo Formentin
 */
class CliMessage {

    /**
     * CliMessage constructor
     */
    CliMessage() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    /**
     * Print one empty line
     */
    void println() {
        AnsiConsole.out.println();
    }

    /**
     * Erase screen
     */
    void eraseScreen() {
        for (int i = 0; i < 50; i++) {
            println();
        }

    }

    /**
     * Show splash Screen
     */
    synchronized void splashScreen() {

        // AnsiConsole.out.println(ansi().eraseScreen().fg(RED).a("BENVENUTO").fg(BLUE).a(" su"));
        AnsiConsole.out().println();
        AnsiConsole.out.println(ansi().fg(YELLOW).a(
                "        | | |           |   |   |   |   |            |  |\n" +
                        "    \\   | | |   /    \\  |   |   |   |   |  /       \\ |  | /\n" +
                        "     \\  | | |  /      \\ |   |   |   |   | /         \\|  |/"));

        AnsiConsole.out.println(ansi().fg(RED).a(
                "        _____         _____ _____            _____       \n" +
                        "       / ____|  /\\   / ____|  __ \\     /\\   |  __ \\   /\\    \n" +
                        "      | (___   /  \\ | |  __| |__) |   /  \\  | |  | | /  \\   \n" +
                        "       \\___ \\ / /\\ \\| | |_ |  _  /   / /\\ \\ | |  | |/ /\\ \\  \n" +
                        "       ____) / ____ \\ |__| | | \\ \\  / ____ \\| |__| / ____ \\ \n" +
                        "      |_____/_/    \\_\\_____|_|  \\_\\/_/    \\_\\_____/_/    \\_\\\n"));
        AnsiConsole.out.println(ansi().fg(YELLOW).a(
                "     /  | | | \\       / |   |   |   |   | \\         /|  |\\\n" +
                        "    /   | | |  \\     /  |   |   |   |   |  \\       / |  | \\                        \n" +
                        "        | | |           |   |   |   |   |            |  |  "));
        AnsiConsole.out().println();
        AnsiConsole.out.println(ansi().fg(BLUE).a("Un divertente gioco di dadi implementato da").fg(GREEN).a(" Matteo Formentin,").fg(MAGENTA).a(" Luca Genoni").fg(DEFAULT).a(" e").fg(RED).a(" Davide Mammarella"));
    }

    //--------------------------
    //  CONNECTION MESSAGES
    //--------------------------

    /**
     * Show request socket or rmi
     */
    synchronized void showSocketRmi() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita 0 per utilizzare RMI, 1 per socket: "));
    }

    /**
     * Show ip request
     */
    synchronized void showIpRequest() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci indirizzo ip del server (0 per default): "));
    }

    /**
     * Show port request
     */
    synchronized void showPortRequest() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci porta del server: "));
    }

    /**
     * Show Connection Successful
     */
    synchronized void showConnectionSuccessful() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Connesso al Server!"));
    }

    /**
     * Show Connection Failed
     */
    synchronized void showConnectionFailed() {
        AnsiConsole.out.println(ansi().fg(RED).a("Impossibile connettersi al server! Riprova"));

    }

    /**
     * Show insert nickname request
     */
    synchronized void showInsertNickname() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci il tuo nome: "));
    }

    /**
     * Show Login successful
     *
     * @param nickname player nickname
     */
    synchronized void showWelcomeNickname(String nickname) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Benvenuto ").fg(BLUE).a(nickname));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Registrato nella lobby. Attendi che tutti i giocatori siano connessi. "));
    }

    /**
     * Show Login failed
     */
    synchronized void showNicknameExists() {
        AnsiConsole.out.println(ansi().fg(RED).a("Esiste già un giocatore con il tuo nome. Scegline un altro."));
    }

    //--------------------------
    //  INIT MESSAGES
    //--------------------------

    /**
     * Show some game information
     *
     * @param playersName array with name of all players
     */
    synchronized void showGameStarted(String[] playersName) {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Tutti i giocatori sono connessi, la partita inizierà a breve!"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("Partecipano " + playersName.length + " giocatori:"));
        for (int i = 0; i < playersName.length; i++) {
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("- Giocatore " + (i + 1) + ": " + playersName[i]));
        }
        AnsiConsole.out.println();
    }

    /**
     * Show Initial Window Pattern Card Selection request
     */
    synchronized void showInitialWindowPatternCardSelection() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita 0..3 per selezionare la tua card: "));
    }

    //--------------------------
    //  GAME MESSAGES
    //--------------------------

    /**
     * Show Your Turn Screen
     */
    synchronized void showYourTurnScreen() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("---------------------------------------------"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("|                ").fg(MAGENTA).a("Tocca a te!").fg(GREEN).a("                |"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("---------------------------------------------"));
    }

    /**
     * Show wait Turn Screen
     *
     * @param name opponent turn name
     */
    synchronized void showWaitYourTurnScreen(String name) {
        AnsiConsole.out.println(ansi().fg(RED).a("Tocca a " + name));
        AnsiConsole.out.println(ansi().fg(RED).a("Aspetta che finisca il suo turno!"));
    }

    /**
     * Show one Window Pattern Card
     *
     * @param card Window Pattern Card
     */
    synchronized void showWindowPatternCard(WindowPatternCard card) {
        AnsiConsole.out().println();
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Nome: " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Difficoltà: " + card.getDifficulty()));

        Cell[][] matrix = card.getMatrix();

        //  --- --- --- --- ---
        // | 1 | @ |   |   |   |
        //  --- --- --- --- ---
        // |   |   |   |   |   |
        //  --- --- --- --- ---

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(" --- --- --- --- ---"));
        for (int m = 0; m < 4; m++) {
            AnsiConsole.out.print(ansi().fg(DEFAULT).a("|"));

            for (int n = 0; n < 5; n++) {
                AnsiConsole.out.print(ansi().fg(DEFAULT).a(" "));

                if (matrix[m][n].getDice() != null) {
                    showDice(matrix[m][n].getDice());
                } else if (matrix[m][n].getColorRestriction() != null) {
                    switch (matrix[m][n].getColorRestriction()) {
                        case BLUE:
                            AnsiConsole.out.print(ansi().fg(BLUE).a("@"));
                            break;
                        case PURPLE:
                            AnsiConsole.out.print(ansi().fg(MAGENTA).a("@"));
                            break;
                        case YELLOW:
                            AnsiConsole.out.print(ansi().fg(YELLOW).a("@"));
                            break;
                        case GREEN:
                            AnsiConsole.out.print(ansi().fg(GREEN).a("@"));
                            break;
                        case RED:
                            AnsiConsole.out.print(ansi().fg(RED).a("@"));
                            break;
                    }
                } else {
                    switch (matrix[m][n].getValueRestriction()) {
                        case 0:
                            AnsiConsole.out.print(ansi().fg(DEFAULT).a(" "));
                            break;
                        default:
                            AnsiConsole.out.print(ansi().fg(DEFAULT).a(matrix[m][n].getValueRestriction()));
                            break;
                    }
                }
                AnsiConsole.out.print(ansi().fg(DEFAULT).a(" |"));
            }
            AnsiConsole.out.println();
            AnsiConsole.out.println(ansi().fg(DEFAULT).a(" --- --- --- --- ---"));
        }
    }

    /**
     * Show Objective Public Card
     */
    synchronized void showObjectivePublicCardMessage() {
        AnsiConsole.out.println(ansi().fg(BLUE).a("Carte obiettivo pubbliche:"));

    }

    /**
     * Show Objective Public Card
     *
     * @param card to show
     */
    synchronized void showObjectivePublicCard(ObjectivePublicCard card) {
        AnsiConsole.out.println(ansi().fg(BLUE).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti: " + card.getPoint()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    /**
     * Show Objective Private Card
     */
    synchronized void showObjectivePrivateCardMessage() {
        AnsiConsole.out.println(ansi().fg(RED).a("Carta obiettivo privata:"));
    }

    /**
     * Show Objective Private Card
     *
     * @param card to show
     */
    synchronized void showObjectivePrivateCard(ObjectivePrivateCard card) {
        AnsiConsole.out.println(ansi().fg(RED).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    /**
     * Show Opponent Window Message
     */
    synchronized void showOpponentWindowMessage() {
        AnsiConsole.out.println(ansi().fg(YELLOW).a("Carte vetrate degli avversari:"));
    }

    /**
     * Show Opponent insert dice move
     *
     * @param name   opponent name
     * @param line   line of opponent window
     * @param column column of opponent
     */
    synchronized void showOpponentInsertDice(String name, int line, int column) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(name + " ha inserito un dado in (" + line + "," + column + ")"));
    }

    /**
     * Show Opponent Window
     *
     * @param name opponent name
     */
    synchronized void showOpponentWindow(String name) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Vetrata di " + name));
    }

    /**
     * Show Tool Card
     */
    synchronized void showToolCardMessage() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Carte utensile:"));
    }


    /**
     * Show Tool Card
     *
     * @param card to show
     */
    synchronized void showToolCard(ToolCard card) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti necessari: " + card.getFavorToken()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }


    /**
     * Show Dice
     *
     * @param dice to show
     */
    synchronized void showDice(Dice dice) {
        Color color = DEFAULT;
        switch (dice.getColor()) {
            case RED:
                color = RED;
                break;
            case GREEN:
                color = GREEN;
                break;
            case YELLOW:
                color = YELLOW;
                break;
            case BLUE:
                color = BLUE;
                break;
            case PURPLE:
                color = MAGENTA;
                break;
        }

        AnsiConsole.out.print(ansi().fg(color).a(dice.getValue()));
    }


    /**
     * Show Round Track
     *
     * @param roundTrack to show
     */
    synchronized void showRoundTrack(DiceStack[] roundTrack) {
        if (roundTrack[0] != null) {
            for (int m = 0; m < roundTrack.length; m++) {
                if (roundTrack[m] == null) break;
                AnsiConsole.out.println(ansi().fg(DEFAULT).a("Round " + (m + 1)));
                showDiceStack(roundTrack[m]);
                AnsiConsole.out.println();
            }
        } else {
            AnsiConsole.out.println(ansi().fg(RED).a("Non ci sono ancora dadi nella riserva!"));
        }
    }


    //--------------------------
    //  MENU MESSAGES
    //--------------------------

    /**
     * Show main menu
     */
    synchronized void showMainMenu() {
        AnsiConsole.out.println();
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Cosa vuoi fare?"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("1 - Pescare e Posizionare un dado"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("2 - Utilizza una carta utensile"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("3 - Termina il turno"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("4 - Visualizza il tuo obiettivo privato"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("5 - Visualizza gli obiettivi pubblici"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("6 - Visualizza le vetrate degli avversari"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("7 - Visualizza la riserva dei dadi"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("8 - Visualizza il tracciato del round"));
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita il numero corrispondente: "));
    }

    /**
     * Show insert row
     */
    synchronized void showChoiceRow() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Quale riga della window vuoi selezionare?"));
    }

    /**
     * Show insert column
     */
    synchronized void showChoiceColumn() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Quale colonna della window vuoi selezionare? "));
    }

    /**
     * Show insert round
     */
    synchronized void showChoiceRound() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Quale round vuoi selezionare? "));
    }

    /**
     * Show insert round dice
     */
    synchronized void showChoiceInRound() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Quale dado del round vuoi selezionare? "));
    }

    /**
     * Show insert dice value
     */
    synchronized void showValueDice() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Quale valore vuoi impostare per il dado? "));
    }

    /**
     * Show increment or decrement dice choice
     */
    synchronized void showIncrementDecrement() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Inserisci 0 per decrementare 1 per incrementare il valore "));
    }

    /**
     * Show dice stack
     *
     * @param diceStack to show
     */
    synchronized void showDiceStack(DiceStack diceStack) {
        for (int i = 0; i < diceStack.size(); i++) {
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(i + ": "));
            showDice(diceStack.get(i));
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(" | "));
        }
        AnsiConsole.out.println();
    }

    /**
     * Show dice pool
     *
     * @param diceStack to show
     */
    synchronized void showDicePool(DiceStack diceStack) {
        showDiceStack(diceStack);
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita il numero corrispondente al dado che vuoi inserire: "));
    }

    /**
     * Show dice hand
     *
     * @param hand to show
     */
    synchronized void showHandPlayer(DiceStack hand) {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Dadi in mano: "));
        if (hand == null || hand.isEmpty()) {
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(" Nessuno "));
            println();
        } else {
            showDiceStack(hand);
        }
    }

    /**
     * Show all tool card
     *
     * @param toolCard to show
     */
    synchronized void showToolCardChoice(ToolCard[] toolCard) {
        for (int i = 0; i < toolCard.length; i++) {
            showToolCard(toolCard[i]);
        }
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita quale toolcard vuoi usare 1°, 2°, 3°: "));
    }

    /**
     * Show input not valid
     */
    synchronized void showInputNotValid() {
        AnsiConsole.out.print(ansi().fg(RED).a("Valore inserito non valido. Riprova: "));
    }

    /**
     * Show insert one character to continue
     */
    synchronized void showWaitInput() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita un carattere per continuare"));
    }

    /**
     * Show round and turn
     *
     * @param round current round
     * @param turn  current turn
     */
    synchronized void showRoundAndTurn(int round, int turn) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("      Round: " + round));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("      Turn: " + turn));
    }

    /**
     * Show one message with red color
     *
     * @param message to show
     */
    synchronized void showMessage(String message) {
        println();
        AnsiConsole.out.println(ansi().fg(RED).a(message));
    }

    /**
     * Show one message with green color
     *
     * @param message to show
     */
    synchronized void showGreenMessage(String message) {
        println();
        AnsiConsole.out.println(ansi().fg(GREEN).a(message));
    }

    /**
     * Show timer is expired
     */
    synchronized void showMoveTimeoutExpired() {
        println();
        AnsiConsole.out.println(ansi().fg(RED).a("Spiacente, hai esaurito il tempo a tua disposizione!"));
    }

    /**
     * Show relogin request
     */
    synchronized void showReLogin() {
        println();
        AnsiConsole.out.println(ansi().fg(RED).a("E'caduta la connessione."));
        AnsiConsole.out.println(ansi().fg(GREEN).a("Digita 0 per ricollegarti, 1 per uscire. Ricordati di usare il vecchio nickname."));
    }

    /**
     * Show end game screen
     *
     * @param ranking     point value
     * @param playersName players name
     * @param myId        player id
     */
    synchronized void showEndGameScreen(int[][] ranking, String[] playersName, int myId) {

        //Se hai vinto
        if (myId == ranking[0][0]) {
            AnsiConsole.out.println(ansi().fg(GREEN).a("                                                                                           ,---,  \n" +
                    "        ,--,                                                                            ,`--.' |  \n" +
                    "      ,--.'|                                                             ___            |   :  :  \n" +
                    "   ,--,  | :            ,--,                  ,---. ,--,               ,--.'|_          '   '  ;  \n" +
                    ",---.'|  : '          ,--.'|                 /__./,--.'|        ,---,  |  | :,'   ,---. |   |  |  \n" +
                    "|   | : _' |          |  |,             ,---.;  ; |  |,     ,-+-. /  | :  : ' :  '   ,'\\'   :  ;  \n" +
                    ":   : |.'  | ,--.--.  `--'_            /___/ \\  | `--'_    ,--.'|'   .;__,'  /  /   /   |   |  '  \n" +
                    "|   ' '  ; :/       \\ ,' ,'|           \\   ;  \\ ' ,' ,'|  |   |  ,\"' |  |   |  .   ; ,. '   :  |  \n" +
                    "'   |  .'. .--.  .-. |'  | |            \\   \\  \\: '  | |  |   | /  | :__,'| :  '   | |: ;   |  ;  \n" +
                    "|   | :  | '\\__\\/: . .|  | :             ;   \\  ' |  | :  |   | |  | | '  : |__'   | .; `---'. |  \n" +
                    "'   : |  : ;,\" .--.; |'  : |__            \\   \\   '  : |__|   | |  |/  |  | '.'|   :    |`--..`;  \n" +
                    "|   | '  ,//  /  ,.  ||  | '.'|            \\   `  |  | '.'|   | |--'   ;  :    ;\\   \\  /.--,_     \n" +
                    ";   : ;--';  :   .'   ;  :    ;             :   \\ ;  :    |   |/       |  ,   /  `----' |    |`.  \n" +
                    "|   ,/    |  ,     .-.|  ,   /               '---\"|  ,   /'---'         ---`-'          `-- -`, ; \n" +
                    "'---'      `--`---'    ---`-'                      ---`-'                                 '---`\"  \n" +
                    "                                                                                                  "));
        }

        for (int i = 0; i < ranking.length; i++) {
            if (myId == i)
                AnsiConsole.out.println(ansi().fg(BLUE).a((i + 1) + "° Posto: " + playersName[ranking[i][0]]) + " Punti: " + ranking[i][1]);
            else
                AnsiConsole.out.println(ansi().fg(DEFAULT).a((i + 1) + "° Posto: " + playersName[ranking[i][0]]) + " Punti: " + ranking[i][1]);
        }
    }
}
