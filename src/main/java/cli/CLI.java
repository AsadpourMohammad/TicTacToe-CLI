package cli;

import file.MyFile;
import game.Game;
import game.Player;
import game.Symbol;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Locale;
import java.util.Scanner;

import static utils.ConsoleColors.*;

public class CLI {
    private static Game game;

    public static String help() {
        return String.format("""
                %s/start player1 player2%s      %s:%2$s       %sTo begin a new game between Player One & Player Two%2$s
                %1$s/put n%2$s                      %3$s:%2$s       %4$sTo fill the n'th spot in the board%2$s
                %1$s/save%2$s                       %3$s:%2$s       %4$sTo save the game and see the game name%2$s
                %1$s/load gameName%2$s              %3$s:%2$s       %4$sTo load the game based on the game name%2$s
                """, PURPLE_BOLD_BRIGHT, RESET, WHITE_BOLD_BRIGHT, CYAN_BOLD_BRIGHT);
    }

    public static String run(String cmd) {
        if (cmd.isEmpty())
            return "";

        String[] tokens = cmd.split("\\s+");

        if (tokens[0].equals("/start") && tokens.length == 3) {
            return start(new Player(RED_BOLD_BRIGHT + tokens[1].toUpperCase(Locale.ROOT) + RESET, Symbol.X),
                    new Player(GREEN_BOLD_BRIGHT + tokens[2].toUpperCase(Locale.ROOT) + RESET, Symbol.O));
        } else if (tokens[0].equals("/put") && tokens.length == 2 && game != null) {
            return put(tokens[1]);
        } else if (tokens[0].equals("/load") && tokens.length == 2) {
            return load(tokens[1]);
        } else if (tokens[0].equals("/save") && tokens.length == 1 && game != null) {
            return save();
        } else
            return BLUE_BOLD_BRIGHT + "COMMAND WAS NOT UNDERSTOOD.\n" + RESET;
    }

    public static String announceStart() {
        return String.format("%s %sVS%s %s", game.getPlayer1(), WHITE_BOLD_BRIGHT, RESET, game.getPlayer2());
    }

    public static String announceTurn() {

        return String.format("%s %s--%s", game.getTurn(), WHITE_BOLD_BRIGHT, RESET);
    }

    public static String announceEnd() {
        return RED_BOLD_BRIGHT + String.format("WINNER === %s", game.getWinner()) + RESET;
    }

    public static String start(Player player1, Player player2) {
        game = new Game(player1, player2);

        return String.format("""
                %s
                %s
                %s""", announceStart(), game.getGameBoard(), announceTurn());
    }

    public static String load(String gameName) {
        game = MyFile.readGame(gameName);

        if (game == null) {
            return "GAME NOT FOUND!\n";
        } else if (game.isOver()) {
            return game.getGameBoard() + "\n" + announceEnd();
        } else return WHITE_BOLD_BRIGHT + "CONTINUING THE GAME BETWEEN...\n" + RESET +
                String.format("""
                        %s
                        %s
                        %s""", announceStart(), game.getGameBoard(), announceTurn());
    }

    public static String put(String n) {
        if (!NumberUtils.isCreatable(n)) {
            return BLUE_BOLD_BRIGHT + "PLEASE ENTER A VALID NUMBER!\n" + RESET + announceTurn();
        } else if (game.set(Integer.parseInt(n))) {
            MyFile.writeGame(game);

            if (game.isOver())
                return game.getGameBoard() + "\n" + announceEnd();
            else
                return game.getGameBoard() + "\n" + announceTurn();
        } else
            return BLUE_BOLD_BRIGHT + "PLEASE CHOOSE ANOTHER SPOT!\n" + RESET + announceTurn();
    }

    public static String save() {
        MyFile.writeGame(game);

        return String.format(BLUE_BOLD_BRIGHT + "GAME SAVED AS '%s'." + RESET, game.getGameName());
    }

    public static void main(String[] args) {
        System.out.println(help());
        Scanner scanner = new Scanner(System.in);

        String input;
        String output;

        while (true) {
            System.out.printf("%s>%s ", WHITE_BOLD_BRIGHT, RESET);

            input = scanner.nextLine();
            output = CLI.run(input.trim().toLowerCase(Locale.ROOT));

            if (output.contains("WINNER") || output.contains("SAVED")) {
                System.out.println(output);
                break;
            } else if (output.equals("")) {
                continue;
            } else {
                System.out.print(output);
            }
        }
    }
}
