import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class CLI {
    private static Game game;

    public static String run(String cmd) throws IOException {
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
            return save("save");
        } else
            return "Command was not understood.";
    }

    public static String start(Player player1, Player player2) throws IOException {
        game = new Game(player1,player2);

        MyFile.startGame(String.valueOf(game.getGameNumber()), game);

        return String.format("""
                New game began between %s and %s.
                %s, please make your move.
                """, game.getPlayer1(), game.getPlayer2(), game.getTurn()) + game.getGameBoard();
    }

    public static String load(String gameName) throws IOException {
        game = MyFile.loadGame(gameName);

        if (game.hasContestantWon()) {
            return game.getGameBoard() + String.format("%s won this game.",game.getTurn());
        } else if (game.isTheGameOver()) {
            return game.getGameBoard() + "The game finished with a tie.";
        } else return String.format("""
                Match continues between %s and %s.
                %s, please make your move.
                """, game.getPlayer1(), game.getPlayer2(), game.getTurn()) + game.getGameBoard();
    }

    public static String put(String n) {
        return game.set(n);
    }

    public static String save(String situation) throws IOException {
        MyFile.writer.write(game.getPlayer1().toString() + " " +  game.getPlayer2().toString() + "\n");
        MyFile.writeBoard(game.getBoard());
        MyFile.writer.write("Turn = " + game.getTurn());
        MyFile.writer.close();

        if (situation.equals("finished")) {
            return "";
        } else if (situation.equals("save")) {
            return String.format("Game saved for later. Your game is named 'game%s'.", game.getGameNumber()) ;
        }

        return "";
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String input;
        String output;

        while (true) {
            System.out.print(">");

            input = scanner.nextLine();
            output = CLI.run(input.trim().toLowerCase(Locale.ROOT));

            if (output.contains("finished")) {
                System.out.println(output);
                CLI.save("finished");
                break;
            } else if (output.contains("Game saved")) {
                System.out.println(output);
                try {
                    CLI.save("save");
                } catch (IOException ignored) {}
                break;
            } else if (output.equals("")) {
                continue;
            } else {
                System.out.println(output);
            }
        }
    }
}
