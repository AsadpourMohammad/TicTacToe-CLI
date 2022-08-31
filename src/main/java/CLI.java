import java.util.Locale;
import java.util.Scanner;

public class CLI {
    private static Game game;

    public static String run(String cmd) {
        if (cmd.isEmpty()) return "";


        String[] tokens = cmd.split("\\s+");

        if (tokens[0].equals("/start") && tokens.length == 3) {
            return start(new Player(tokens[1]), new Player(tokens[2]));
        }
        else if (tokens[0].equals("/put") && tokens.length == 2) {
            return put(tokens[1]);
        }
        else if (tokens[0].equals("/load") && tokens.length == 2) {
            return load(tokens[1]);
        }
        else if (tokens[0].equals("/save") && tokens.length == 1) {
            return save();
        } else
            return "Command was not understood.";
    }

    public static String start(Player player1, Player player2) {
        game = new Game(player1,player2);

        return String.format("""
                New game began between %s and %s.
                %s, please make your move.
                """, game.getPlayer1(), game.getPlayer2(), game.getTurn()) + game.getGameBoard();
    }

    public static String load(String gameName) {
        game = MyFile.readGame(gameName);

        if (game == null) {
            return "No such game was found.";
        } else if (game.hasContestantWon()) {
            return game.getGameBoard() + String.format("This game finished with %s winning this game.",game.getTurn());
        } else if (game.isTheGameOver()) {
            return game.getGameBoard() + "The game finished with a tie.";
        } else return String.format("""
                Match continues between %s and %s.
                %s, please make your move.
                """, game.getPlayer1(), game.getPlayer2(), game.getTurn()) + game.getGameBoard();
    }

    public static String put(String n) {
        String setReturned = game.set(n);

        if (game.isEnded())
            finished();

        return setReturned;
    }

    public static String save() {
        MyFile.writeGame(String.valueOf(game.getGameNumber()), game);

        return String.format("Game saved for later. Your game is named 'game%s'.", game.getGameNumber());
    }

    public static void finished() {
        MyFile.writeGame(String.valueOf(game.getGameNumber()), game);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input;
        String output;

        while (true) {
            System.out.print(">");

            input = scanner.nextLine();
            output = CLI.run(input.trim().toLowerCase(Locale.ROOT));

            if (output.contains("finished") || output.contains("saved")) {
                System.out.println(output);
                break;
            } else if (output.equals("")) {
                continue;
            } else {
                System.out.println(output);
            }
        }
    }
}
