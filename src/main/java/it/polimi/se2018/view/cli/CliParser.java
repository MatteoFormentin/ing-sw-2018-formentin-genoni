package it.polimi.se2018.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

class CliParser {
    private Scanner in;
    private CliMessage cliMessage;

    CliParser() {
        in = new Scanner(System.in);
        cliMessage = new CliMessage();
    }

    int parseInt() {
        int parsed = 0;
        boolean flag = false;
        while (!flag) {
            try {
                parsed = in.nextInt();
                flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
            }
        }
        return parsed;
    }

    String parseNickname() {
        String parsed = "";
        boolean flag = false;
        while (!flag) {
            try {
                parsed = in.next(" [a-zA-Z_0-9]{10}");
                flag = true;
            } catch (InputMismatchException ex) {
                cliMessage.showInputNotValid();
            }
        }
        return parsed;
    }

}
