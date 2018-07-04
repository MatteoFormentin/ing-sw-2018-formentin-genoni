package it.polimi.se2018.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Contains CLI input parser function.
 *
 * @author Matteo Formentin
 */
public class CliParser {
    private CliMessage cliMessage;

    public CliParser() {
        cliMessage = new CliMessage();
    }

    public int readSplash() {
        Scanner in = new Scanner(System.in);
        cliMessage.showWaitInput();
        in.next();
        return 0;
    }

    public int parseInt() {
        int parsed = 0;
        boolean flag = false;
        while (!flag) {
            Scanner in = new Scanner(System.in);
            try {
                parsed = in.nextInt();
                flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
                in.next();
            }
        }
        return parsed;
    }

    public int parsePositiveInt(int upperBound) {
        int parsed = 0;
        boolean flag = false;
        do {
            Scanner in = new Scanner(System.in);
            try {
                parsed = in.nextInt();
                if (!(parsed <= 0 || parsed > upperBound)) flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
                in.next();
            }
        } while (!flag);
        return parsed;
    }

    public int parseInt(int upperBound) {
        boolean flag = false;
        int parsed;
        do {
            parsed = parseInt();
            if (!(parsed < 0 || parsed > upperBound)) {
                flag = true;
            } else {
                cliMessage.showInputNotValid();
            }
        } while (!flag);
        return parsed;
    }

    public String parseNickname() {
        String parsed = "";
        boolean flag = false;
        while (!flag) {
            Scanner in = new Scanner(System.in);
            try {
                parsed = in.next("([a-z]|[A-z]|[0-9]){0,11}");
                flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
                in.next();
            }
        }
        return parsed;
    }

    public String parseIp() {
        String parsed = "";
        boolean flag = false;
        while (!flag) {
            Scanner in = new Scanner(System.in);
            try {
                parsed = in.next("(localhost)|(0)|((([1]?[0-9]?[0-9])|([2][0-5][0-5]))[.](([1]?[0-9]?[0-9])|([2][0-5][0-5]))[.](([1]?[0-9]?[0-9])|([2][0-5][0-5]))[.](([1]?[0-9]?[0-9])|([2][0-5][0-5])))");
                flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
                in.next();
            }
        }
        return parsed;
    }

    public int parsePort(int lowBound, int upperBound, int alreadyTaken) {
        boolean flag = false;
        int parsed;
        do {
            parsed = parseInt();
            if (!(parsed < lowBound || parsed > upperBound || parsed == alreadyTaken)) {
                flag = true;
            } else {
                cliMessage.showInputNotValid();
            }
        } while (!flag);
        return parsed;
    }

}
