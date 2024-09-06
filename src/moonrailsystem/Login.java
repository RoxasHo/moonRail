package moonrailsystem;

import java.util.Scanner;

public class Login {
    private String userID;
    static Scanner input = new Scanner(System.in);
    
    public String getId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your id: ");
        userID = scanner.nextLine();
        return userID;
    }

    public void displayid() {
        System.out.println(userID + "\t");
    }

    public String toString() {
        return userID + "\n";
    }

    public Login() {
    }

    public Login(String record) {
        String[] data = record.split(" ");
        userID = data[0];
    }
}

