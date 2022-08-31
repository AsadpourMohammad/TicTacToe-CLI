import java.io.*;

public class MyFile {
    public static String path = System.getProperty("user.dir") + "\\gameFiles";

    public static void writeGame(String gameNum, Game game) {
        String newPath = path + String.format("\\game%s.txt", gameNum);

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(newPath))) {
            out.writeObject(game);
        } catch (IOException ignored) {}

        newPath = path + "\\gameNumFile.txt";
        File file = new File(newPath);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(gameNum);
        } catch (IOException ignored) {}
    }

    public static Game readGame(String gameName) {
        String newPath = path + String.format("\\%s.txt", gameName);

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(newPath)))
        {
            return (Game) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static int gameNum() {
        String newPath = path + "\\gameNumFile.txt";
        File gameNumFile = new File(newPath);
        try(BufferedReader reader = new BufferedReader(new FileReader(gameNumFile))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException ignored){}
        return 0;
    }
}
