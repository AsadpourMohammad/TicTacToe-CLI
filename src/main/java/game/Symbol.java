package game;

import static utils.ConsoleColors.*;

public enum Symbol {
    X(RED_BOLD_BRIGHT + "X" + RESET),
    O(GREEN_BOLD_BRIGHT + "O" + RESET);

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
