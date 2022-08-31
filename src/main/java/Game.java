import org.apache.commons.lang3.math.NumberUtils;

public class Game {
    private final Player player1;
    private final Player player2;

    private Player turn;
    private String turnSymbol;

    private final int gameNumber;
    private static int gameNum = MyFile.gameNum();

    private final String[] board;
    private String gameBoard;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = this.player1;
        this.turnSymbol = "X";
        gameNumber = gameNum;
        gameNum++;

        board = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        gameBoard = String.format("""
            |---|---|---|
            | %s | %s | %s |
            |-----------|
            | %s | %s | %s |
            |-----------|
            | %s | %s | %s |
            |---|---|---|
            """, (Object[]) board);
    }

    public Game(Player player1, Player player2, Player turn, int gameNumber, String[] board) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = turn;
        setTurn();
        this.gameNumber = gameNumber;
        this.board = board;

        gameBoard = String.format("""
            |---|---|---|
            | %s | %s | %s |
            |-----------|
            | %s | %s | %s |
            |-----------|
            | %s | %s | %s |
            |---|---|---|
            """, (Object[]) board);
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getTurn() {
        return turn;
    }

    public void setTurn() {
        if (turn == player1) {
            turn = player2;
            turnSymbol = "O";
        }
        else {
            turn = player1;
            turnSymbol = "X";
        }
    }

    public String set(String n) {
        if (gameBoard.contains(String.valueOf(n))) {
            board[Integer.parseInt(n)-1] = String.valueOf(turnSymbol);
            gameBoard = gameBoard.replace(n.charAt(0), turnSymbol.charAt(0));

            if (hasContestantWon()) {
                return gameBoard + String.format("%s has won the game. Game finished.",turn);
            }

            if (isTheGameOver()) {
                return gameBoard + "Game finished with a tie.";
            }
            setTurn();
            return gameBoard + String.format("""
                %s, please make your move.
                """, turn);
        }
        else
            return "You can't mark there. Please chose another place.";
    }

    public boolean isTheGameOver() {
        for (int i = 0; i < 9; i++) {
            if (NumberUtils.isCreatable(board[i])) return false;
        }
        return true;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public String[] getBoard() {
        return board;
    }

    public boolean hasContestantWon() {
        return ( board[0].equals(turnSymbol) && board[1].equals(turnSymbol) && board[2].equals(turnSymbol) ) ||
                ( board[3].equals(turnSymbol) && board[4].equals(turnSymbol) && board[5].equals(turnSymbol) ) ||
                ( board[6].equals(turnSymbol) && board[7].equals(turnSymbol) && board[8].equals(turnSymbol) ) ||

                ( board[0].equals(turnSymbol) && board[3].equals(turnSymbol) && board[6].equals(turnSymbol) ) ||
                ( board[1].equals(turnSymbol) && board[4].equals(turnSymbol) && board[7].equals(turnSymbol) ) ||
                ( board[2].equals(turnSymbol) && board[5].equals(turnSymbol) && board[8].equals(turnSymbol) ) ||

                ( board[0].equals(turnSymbol) && board[4].equals(turnSymbol) && board[8].equals(turnSymbol) ) ||
                ( board[2].equals(turnSymbol) && board[4].equals(turnSymbol) && board[6].equals(turnSymbol) );
    }
}
