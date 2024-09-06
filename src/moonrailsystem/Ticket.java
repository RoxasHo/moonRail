package moonrailsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ticket {
    private static final String FILE_PATH = "reservation.txt";

    public static void printTicket() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the ticket.");
        }
    }
}
