package it.polimi.se2018.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Contains non blocking CLI input parser function.
 *
 * @author Matteo Formentin
 */
public class CliParserNonBlocking extends CliParser {

    private AtomicBoolean isInputActive;

    private CliMessage cliMessage;

    /**
     * CliParser constructor
     */
    public CliParserNonBlocking(AtomicBoolean isInputActive) {
        cliMessage = new CliMessage();
        this.isInputActive = isInputActive;
    }

    /**
     * Read one random character
     */
    public int readSplash() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        flush(bufferedReader);
        cliMessage.showWaitInput();
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

    /**
     * Read one int
     */
    public int parseInt() {
        int parsed = 0;
        boolean flag = false;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        flush(bufferedReader);
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

    /**
     * Read one int
     *
     * @param upperBound max int to parse
     */
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

    /**
     * Read one positive int
     *
     * @param upperBound max int to parse
     */
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

    /**
     * Flush input
     */
    private void flush(BufferedReader bufferedReader) {
        try {
            if (bufferedReader.ready()) {
                bufferedReader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
