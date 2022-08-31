package game;

import java.io.Serializable;

public record Player(String name, Symbol symbol) implements Serializable {
    @Override
    public String toString() {
        return String.format("%s(%s)", name, symbol);
    }
}
