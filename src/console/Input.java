package console;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String getString() {
        return scanner.nextLine();
    }

    public static String getLine() {
        return scanner.nextLine();
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
