package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
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

    CliMessage() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    void println() {
        AnsiConsole.out.println();
    }

    void eraseScreen() {
        for (int i = 0; i < 50; i++) {
            println();
        }

    }

    void splashScreen() {

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
    void showIpRequest() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci indirizzo ip del server (0 per default): "));
    }

    void showPortRequest() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci porta del server: "));
    }

    void showConnectionSuccessful() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Connesso al Server!"));
    }

    void showConnectionFailed() {
        AnsiConsole.out.println(ansi().fg(RED).a("Impossibile connettersi al server! Riprova"));

    }

    void showInsertNickname() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Inserisci il tuo nome: "));
    }

    void showWelcomeNickname(String nickname) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Benvenuto ").fg(BLUE).a(nickname));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Registrato nella lobby. Attendi che tutti i giocatori siano connessi. "));
    }

    void showNicknameExists() {
        AnsiConsole.out.println(ansi().fg(RED).a("Esiste già un giocatore con il tuo nome. Scegline un altro."));
    }

    //--------------------------
    //  INIT MESSAGES
    //--------------------------

    void showGameStarted(String[] playersName) {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Tutti i giocatori sono connessi, la partita inizierà a breve!"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("Partecipano " + playersName.length + " giocatori:"));
        for (int i = 0; i < playersName.length; i++) {
            AnsiConsole.out.println(ansi().fg(DEFAULT).a("- Giocatore " + (i + 1) + ": " + playersName[i]));
        }
        AnsiConsole.out.println();
    }

    void showInitialWindowPatternCardSelection() {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita 0..3 per selezionare la tua card: "));
    }

    //--------------------------
    //  GAME MESSAGES
    //--------------------------

    void showYourTurnScreen() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("---------------------------------------------"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("|                ").fg(MAGENTA).a("Tocca a te!").fg(GREEN).a("                |"));
        AnsiConsole.out.println(ansi().fg(GREEN).a("---------------------------------------------"));
    }

    void showWaitYourTurnScreen(String name) {
        AnsiConsole.out.println(ansi().fg(RED).a("Tocca a " + name));
        AnsiConsole.out.println(ansi().fg(RED).a("Aspetta che finisca il suo turno!"));
    }

    void showWindowPatternCard(WindowPatternCard card) {
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

    void showObjectivePublicCardMessage() {
        AnsiConsole.out.println(ansi().fg(BLUE).a("Carte obiettivo pubbliche:"));

    }

    void showObjectivePublicCard(ObjectivePublicCard card) {
        AnsiConsole.out.println(ansi().fg(BLUE).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti: " + card.getPoint()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    void showObjectivePrivateCardMessage() {
        AnsiConsole.out.println(ansi().fg(RED).a("Carta obiettivo privata:"));
    }

    void showObjectivePrivateCard(ObjectivePrivateCard card) {
        AnsiConsole.out.println(ansi().fg(RED).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    void showOpponentWindowMessage() {
        AnsiConsole.out.println(ansi().fg(YELLOW).a("Carte vetrate degli avversari:"));
    }

    void showOpponentInsertDice(String name,int line, int column) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(name + " ha inserito un dado in ("+line+","+column+")"));
    }
    void showOpponentWindow (String name){
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Vetrata di "+name));
    }


    void showToolCardMessage() {
        AnsiConsole.out.println(ansi().fg(GREEN).a("Carte utensile:"));
    }

    void showToolCard(ToolCard card) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti necessari: " + card.getFavorToken()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    void showDice(Dice dice) {
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


    //--------------------------
    //  MENU MESSAGES
    //--------------------------

    void showMainMenu() {
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

    void showInsertDiceRow() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("In quale riga vuoi inserire il dado? "));
    }

    void showInsertDiceColumn() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("In quale colonna vuoi inserire il dado?"));
    }


    void showDiceStack(DiceStack diceStack) {
        for (int i = 0; i < diceStack.size(); i++) {
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(i + ": "));
            showDice(diceStack.get(i));
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(" | "));
        }
        AnsiConsole.out.println();
    }

    void showDicePool(DiceStack diceStack) {
        showDiceStack(diceStack);
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita il numero corrispondente al dado che vuoi inserire: "));
    }

    void showHandPlayer(DiceStack hand) {
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Dadi in mano: "));
        if (hand == null || hand.isEmpty()) {
            AnsiConsole.out.print(ansi().fg(DEFAULT).a(" Nessuno "));
            println();
        } else {
            showDiceStack(hand);
        }
    }

    void showToolCardChoise(ToolCard[] toolCard) {
        for (int i = 0; i < toolCard.length; i++) {
            showToolCard(toolCard[i]);
        }
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita quale toolcard vuoi usare 1°, 2°, 3°: "));
    }

    void showInputNotValid() {
        AnsiConsole.out.print(ansi().fg(RED).a("Valore inserito non valido. Riprova: "));
    }

    void showWaitInput() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita un carattere per continuare"));
    }

    void showMessage(String message) {
        println();
        AnsiConsole.out.println(ansi().fg(RED).a(message));
    }

    void showGreenMessage(String message) {
        println();
        AnsiConsole.out.println(ansi().fg(GREEN).a(message));
    }

    void showMoveTimeoutExpired() {
        println();
        AnsiConsole.out.println(ansi().fg(RED).a("Spiacente, hai esaurito il tempo a tua disposizione!"));
    }
}
