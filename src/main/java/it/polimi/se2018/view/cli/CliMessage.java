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
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita un carattere per iniziare"));
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
        AnsiConsole.out.println(ansi().fg(RED).a("---------------------------------------------"));
        AnsiConsole.out.println(ansi().fg(RED).a("|                ").fg(BLUE).a("Tocca a te!").fg(RED).a("                |"));
        AnsiConsole.out.println(ansi().fg(RED).a("---------------------------------------------"));
    }

    void showWaitYourTurnScreen() {
        AnsiConsole.out.println(ansi().fg(RED).a("Tocca a un altro giocatore!"));
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
                        case Blue:
                            AnsiConsole.out.print(ansi().fg(BLUE).a("@"));
                            break;
                        case Purple:
                            AnsiConsole.out.print(ansi().fg(MAGENTA).a("@"));
                            break;
                        case Yellow:
                            AnsiConsole.out.print(ansi().fg(YELLOW).a("@"));
                            break;
                        case Green:
                            AnsiConsole.out.print(ansi().fg(GREEN).a("@"));
                            break;
                        case Red:
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

    void showObjectivePublicCard(ObjectivePublicCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti: " + card.getPoint()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    void showObjectivePrivateCard(ObjectivePrivateCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }


    void showToolCard(ToolCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti necessari: " + card.getFavorToken()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    void showDice(Dice dice) {
        Color color = DEFAULT;
        switch (dice.getColor()) {
            case Red:
                color = RED;
                break;
            case Green:
                color = GREEN;
                break;
            case Yellow:
                color = YELLOW;
                break;
            case Blue:
                color = BLUE;
                break;
            case Purple:
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
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("1 - Posiziona un dado"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("2 - Utilizza una carta utensile"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("3 - Termina il turno"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("4 - Visualizza il tuo obiettivo privato"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("5 - Visualizza gli obiettivi pubblici"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("6 - Visualizza le vetrate degli avversari"));
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Digita il numero corrispondente: "));
    }

    void showInsertDiceRow() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("In quale riga vuoi inserire il dado?"));
    }

    void showInsertDiceColumn() {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("In quale colonna vuoi inserire il dado?"));
    }

    void showDiceStack(DiceStack diceStack) {
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Digita il numero corrispondente al dado che vuoi inserire: "));
        for (int i = 0; i < diceStack.size(); i++) {
            AnsiConsole.out.print(ansi().fg(RED).a(i + ": "));
            this.showDice(diceStack.get(i));
        }
        AnsiConsole.out.println();
    }

    void showInputNotValid() {
        AnsiConsole.out.print(ansi().fg(RED).a("Valore inserito non valido. Riprova: "));
    }

}
