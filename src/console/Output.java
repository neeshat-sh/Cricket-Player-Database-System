package console;

import model.Player;

public class Output {
    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void printError(String message) {
        System.err.println(message);
    }

    public static void printNewLine() {
        System.out.println();
    }

    public static void printPlayer(Player player) {
        System.out.println(player);
    }
}
