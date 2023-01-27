import java.io.*;

public class Main {
    private final static String LOG_PATH = "src/main/resources/log.txt";


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        writer.write(stringBuilder.toString());

        writer.write("Writing to a file.");
        writer.write("\nHere is another line.");

        writer.close();

    }
}