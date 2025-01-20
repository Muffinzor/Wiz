package wizardo.game.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static wizardo.game.Wizardo.player;


public class ErrorLogger {

    public static void logError(String message, Throwable throwable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(formatter);

        String filename = "crashlog_" + timestamp + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            if(player != null) {
                writer.println("Crash occured with this screen on top of the stack: " + player.screen.game.currentScreen + "\n");
            }
            writer.println(message);
            throwable.printStackTrace(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
