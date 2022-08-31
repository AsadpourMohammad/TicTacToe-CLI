package file;

import game.Game;

import java.io.*;

public class MyFile {
    public static String path = System.getProperty("user.dir") + "\\gameFiles";

    public static void writeGame(Game game) {
        String newPath = path + String.format("\\game%s.txt", game.getGameNum());

        boolean temp = new File(path).mkdir();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(newPath))) {
            out.writeObject(game);
        } catch (IOException ignored) {}
    }

    public static Game readGame(String gameName) {
        String newPath = path + String.format("\\%s.txt", gameName);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(newPath))) {
            return (Game) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
