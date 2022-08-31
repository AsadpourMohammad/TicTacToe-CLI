package game;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public record Player(String name, Symbol symbol) implements Serializable {
    @Override
    public String toString() {
        return String.format("%s(%s)", name, symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.hashCode() * new Random().nextInt());
    }
}
