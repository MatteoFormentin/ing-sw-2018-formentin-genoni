package it.polimi.se2018.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class CliParserNonBlocking extends CliParser {

    private AtomicBoolean isInputActive;

    private CliMessage cliMessage;

    public CliParserNonBlocking(AtomicBoolean isInputActive) {
        cliMessage = new CliMessage();
        this.isInputActive = isInputActive;
    }

    public int readSplash() {
        cliMessage.showWaitInput();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (isInputActive.get()) {
            try {
                if (bufferedReader.ready()) {
                    bufferedReader.readLine();
                    return 0;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    public int parseInt() {
        int parsed = 0;
        boolean flag = false;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (!flag && isInputActive.get()) {
            try {
                if (bufferedReader.ready()) {
                    try {
                        parsed = Integer.parseInt(bufferedReader.readLine());
                        flag = true;
                    } catch (NumberFormatException ex) {
                        cliMessage.showInputNotValid();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (!isInputActive.get()) return -1; //Move Timeout
        return parsed;
    }

    public int parseInt(int upperBound) {
        boolean flag = false;
        int parsed;
        do {
            parsed = parseInt();
            if (parsed == -1) {
                return -1;
            } else if (!(parsed < 0 || parsed > upperBound)) {
                flag = true;
            } else {
                cliMessage.showInputNotValid();
            }
        } while (!flag && isInputActive.get());
        if (!isInputActive.get()) return -1; //Move Timeout
        return parsed;
    }

    public int parsePositiveInt(int upperBound) {
        boolean flag = false;
        int parsed;
        do {
            parsed = parseInt();
            if (parsed == -1) {
                return -1;
            } else if (!(parsed <= 0 || parsed > upperBound)) {
                flag = true;
            } else {
                cliMessage.showInputNotValid();
            }
        } while (!flag && isInputActive.get());
        if (!isInputActive.get()) return -1; //Move Timeout
        return parsed;
    }
}
