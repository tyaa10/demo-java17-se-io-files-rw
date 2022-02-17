package org.tyaa.demo.java.se.io;

import java.io.*;

public class OutSwitch {

    private static String logPath =
        String.format("out%sfiles%slog.txt", File.separator, File.separator);
    private static PrintStream outConsole;

    public static void switchToFile () throws FileNotFoundException {
        outConsole = System.out;
        System.setOut(new PrintStream(new FileOutputStream(logPath, true)));
    }

    public static void switchToConsole () {
        System.setOut(outConsole);
    }
}
