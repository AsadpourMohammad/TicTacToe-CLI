import java.io.*;
import java.util.Arrays;

public class MyFile {
    public static FileWriter writer;
    public static String path = System.getProperty("user.dir") + "\\gameFiles";

    public static void startGame(String gameNum, Game game) throws IOException {
        String newPath = path + String.format("\\game%s.txt", gameNum);

        File file = new File(newPath);
        writer = new FileWriter(file);
    }

    public static void writeBoard(String[] board) throws IOException {
        writer.write(Arrays.toString(board) + "\n");
    }

    public static Game loadGame(String gameName) throws IOException {
        String gameNum = gameName.substring(4);

        String newPath = path + String.format("\\game%s.txt", gameNum);

        File file = new File(newPath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String[] names = reader.readLine().split(" ");
        String[] board  = reader.readLine().split(", ");
        board[0] = board[0].substring(1);
        board[8] = board[8].substring(0,1);
        String turn = reader.readLine().substring(7);

        reader.close();
        writer = new FileWriter(file);

        return new Game(new Player(names[0]), new Player(names[1]), new Player(turn), Integer.parseInt(gameNum), board);
    }

    public static int gameNum() {
        FilenameFilter filter = (dir, name) -> name.endsWith(".txt");

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(filter);

        assert listOfFiles != null;
        if (!Arrays.stream(listOfFiles).toList().isEmpty()) {
            String name = "";

            for (File file : listOfFiles) {
                name = file.getName();
            }

            return (Integer.parseInt(name.substring(4,name.indexOf("."))) + 1);
        } else
            return 0;
    }

}
