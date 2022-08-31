import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

public class Game implements Serializable {
    private final Player player1;
    private final Player player2;

    private Player turn;
    private String turnSymbol;

    private final int gameNumber;

    private final String[] board;
    private String gameBoard;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = this.player1;

        this.turnSymbol = "X";
        this.gameNumber = MyFile.gameNum() + 1;

        board = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        gameBoard = String.format("""
             %s | %s | %s
            -- + - + --
             %s | %s | %s
            -- + - + --
             %s | %s | %s
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

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard() {
        this.gameBoard = String.format("""
             %s | %s | %s
            -- + - + --
             %s | %s | %s
            -- + - + --
             %s | %s | %s
            """, (Object[]) board);
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
            setGameBoard();

            if (hasContestantWon()) {
                return gameBoard + String.format("%s has won the game. Game finished.",turn);
            } else if (isTheGameOver()) {
                return gameBoard + "Game finished with a tie.";
            } else {
                setTurn();
                return gameBoard + String.format("""
                %s, please make your move.
                """, turn);
            }

        } else if (NumberUtils.isCreatable(n)) {
            return "You can't mark there. Please chose another place.";
        } else
            return "Please enter a valid number.";
    }

    public boolean isTheGameOver() {
        for (int i = 0; i < 9; i++) {
            if (NumberUtils.isCreatable(board[i])) return false;
        }
        return true;
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
