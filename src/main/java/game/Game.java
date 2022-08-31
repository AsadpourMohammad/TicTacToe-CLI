package game;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static utils.ConsoleColors.*;

public class Game implements Serializable {
    private final Player player1;
    private final Player player2;

    private Player turn;
    private int turnCount = 0;

    private String winner;

    private final String gameName;

    private final String[] board;
    private String gameBoard;

    private boolean over = false;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = this.player1;
        this.gameName =
                StringUtils.capitalize(player1.name().substring(7, player1.name().length() - 4).toLowerCase(Locale.ROOT)) +
                StringUtils.capitalize(player2.name().substring(7, player2.name().length() - 4).toLowerCase(Locale.ROOT)) +
                hashCode();

        this.board = new String[]{
                WHITE_BOLD_BRIGHT + "1" + RESET,
                WHITE_BOLD_BRIGHT + "2" + RESET,
                WHITE_BOLD_BRIGHT + "3" + RESET,
                WHITE_BOLD_BRIGHT + "4" + RESET,
                WHITE_BOLD_BRIGHT + "5" + RESET,
                WHITE_BOLD_BRIGHT + "6" + RESET,
                WHITE_BOLD_BRIGHT + "7" + RESET,
                WHITE_BOLD_BRIGHT + "8" + RESET,
                WHITE_BOLD_BRIGHT + "9" + RESET};
        setGameBoard();
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

    public boolean isOver() {
        return over;
    }

    public String getWinner() {
        return winner;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameBoard() {
        this.gameBoard = String.format("""
                         %3$2s %1$2s|%2$2s %4$2s %1$2s|%2$2s %5$2s
                        %1$s — + — + —%2$s
                         %6$2s %1$2s|%2$2s %7$2s %1$2s|%2$2s %8$2s
                        %1$s — + — + —%2$s
                         %9$2s %1$2s|%2$2s %10$2s %1$2s|%2$2s %11$2s""", CYAN_BOLD_BRIGHT, RESET,
                board[0], board[1], board[2], board[3], board[4], board[5], board[6], board[7], board[8]);
    }

    public boolean set(int n) {
        if (gameBoard.contains(WHITE_BOLD_BRIGHT + n + RESET)) {
            this.board[n - 1] = turn.symbol().getSymbol();
            setGameBoard();

            if (hasContestantWon()) {
                over = true;
                winner = turn.toString();
            } else if (turnCount == 8) {
                over = true;
                winner = "TIE";
            } else {
                turn = (turn == player1) ? player2 : player1;
                turnCount++;
            }
            return true;
        } else
            return false;
    }

    public boolean hasContestantWon() {
        return (board[0].equals(turn.symbol().getSymbol()) && board[1].equals(turn.symbol().getSymbol()) && board[2].equals(turn.symbol().getSymbol())) ||
                (board[3].equals(turn.symbol().getSymbol()) && board[4].equals(turn.symbol().getSymbol()) && board[5].equals(turn.symbol().getSymbol())) ||
                (board[6].equals(turn.symbol().getSymbol()) && board[7].equals(turn.symbol().getSymbol()) && board[8].equals(turn.symbol().getSymbol())) ||

                (board[0].equals(turn.symbol().getSymbol()) && board[3].equals(turn.symbol().getSymbol()) && board[6].equals(turn.symbol().getSymbol())) ||
                (board[1].equals(turn.symbol().getSymbol()) && board[4].equals(turn.symbol().getSymbol()) && board[7].equals(turn.symbol().getSymbol())) ||
                (board[2].equals(turn.symbol().getSymbol()) && board[5].equals(turn.symbol().getSymbol()) && board[8].equals(turn.symbol().getSymbol())) ||

                (board[0].equals(turn.symbol().getSymbol()) && board[4].equals(turn.symbol().getSymbol()) && board[8].equals(turn.symbol().getSymbol())) ||
                (board[2].equals(turn.symbol().getSymbol()) && board[4].equals(turn.symbol().getSymbol()) && board[6].equals(turn.symbol().getSymbol()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }
}
