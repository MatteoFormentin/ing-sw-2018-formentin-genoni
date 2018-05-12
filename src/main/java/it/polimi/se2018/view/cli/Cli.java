package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.card.Deck;
import it.polimi.se2018.model.card.objective_private_card.ObjectivePrivateCard;
import it.polimi.se2018.model.card.objective_public_card.ObjectivePublicCard;
import it.polimi.se2018.model.card.tool_card.ToolCard;
import it.polimi.se2018.model.card.window_pattern_card.Cell;
import it.polimi.se2018.model.card.window_pattern_card.WindowPatternCard;
import it.polimi.se2018.model.dice.Dice;
import it.polimi.se2018.model.dice.FactoryBalancedDice;
import org.fusesource.jansi.*;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


/**
 * @author Matteo Formentin
 */
public class Cli {

    public Cli() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
       /* splashScreen();
        showWindowPatternCard(Deck.getDeck().drawWindowPatternCard());
        showObjectivePublicCard(Deck.getDeck().drawObjectivePublicCard());
        showObjectivePrivateCard(Deck.getDeck().drawObjectivePrivateCard());
        showToolCard(Deck.getDeck().drawToolCard());
        showDice(FactoryBalancedDice.getBalancedDiceFactory().createDice());*/
    }

    public void splashScreen() {
        AnsiConsole.out.println(ansi().eraseScreen().fg(RED).a("WELCOME").fg(BLUE).a(" to").fg(GREEN).a(" SAGRADA").reset());
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("A game implemented by  Matteo Formentin, Luca Genoni and Davide Mammarella"));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Press a button to start"));
    }

    public void showWindowPatternCard(WindowPatternCard card) {
        AnsiConsole.out().println();
        AnsiConsole.out.print(ansi().fg(DEFAULT).a("Nome: " + card.getName()));
        AnsiConsole.out.print(ansi().fg(DEFAULT).a(" - "));
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

                if (matrix[m][n].getColorRestriction() != null) {
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

    public void showObjectivePublicCard(ObjectivePublicCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti: " + card.getPoint()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    public void showObjectivePrivateCard(ObjectivePrivateCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    public void showToolCard(ToolCard card) {
        AnsiConsole.out.println();

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getId() + " - " + card.getName()));
        AnsiConsole.out.println(ansi().fg(DEFAULT).a("Punti necessari: " + card.getFavorToken()));

        AnsiConsole.out.println(ansi().fg(DEFAULT).a(card.getDescription()));
    }

    public void showDice(Dice dice) {
        AnsiConsole.out.println();
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

        AnsiConsole.out.println(ansi().fg(color).a(dice.getValue()));
    }

}
