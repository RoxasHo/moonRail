package moonrailsystem;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import static moonrailsystem.Login.input;

public class MoonRailSystem {
    
    private static final String ADMIN_PASSWORD = "1234";
    private static final TrainRecord[] TRAINS = {
        new TrainRecord(1, "Maglev Train", "Kuala Lumpur", "Johor", 50, 30.0, 100, 15.0, "2022-05-31"),
        new TrainRecord(2, "Bullet Train", "Kuala Lumpur", "Perlis", 40, 20.0, 80, 10.0, "2022-06-01")
    };
    public static void main(String[] args) throws IOException {    
      
//-----------------------start---------------------------------    
    
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("   Welcome To Our MoonRail Reservation System   ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Scanner scanner = new Scanner(System.in);
        int ch;
        do {
            System.out.println("                    Main Menu                   ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1.Admin mode\n2.User mode\n3.Exit");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("Enter your choice: ");
            ch = scanner.nextInt();
            System.out.println();

            switch (ch) {
                case 1:
                    adminMode();
                    break;
                case 2:
                    userMode();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
            }
        } while (ch != 3);
    }

    private static void adminMode() throws IOException{
        Scanner scanner = new Scanner(System.in);  
        System.out.print("Enter the administrator password: ");
        String password = scanner.next();
        System.out.println();

        if (!ADMIN_PASSWORD.equals(password)) {
            System.out.println("Password Input Is Wrong! Try Again");
            System.out.println("You Do Not Have Access to This Mode\n");
            return;
        }

        int choice;
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("              ADMINISTRATOR MENU                ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1.Display All Trains Details");
            System.out.println("2.Display All Reservations");
            System.out.println("3.Add More Users");
            System.out.println("4.Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    displayTrainDetails();
                    break;
                case 2:
                    Ticket.printTicket();
                    break;
                case 3:
                    userManagement();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
            }
        } while (choice != 6);
    }
    
    private static void displayTrainDetails() {
            System.out.println("Train No\tTrain Name\tBoarding Station\tDestination Station\tFirst Class Seats\tFirst Class Fare\tSecond Class Seats\tSecond Class Fare\tDate");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        for (TrainRecord train : TRAINS) {
            System.out.println(train);
        }
        
        System.out.println();
    }
    
    private static void userMode() {
    Login user = new Login();
    String userID = user.getId();

    try (RandomAccessFile file = new RandomAccessFile("id.txt", "r")) {
        String line;
        while ((line = file.readLine()) != null) {
            String[] data = line.split(" ");
            if (data[0].equals(userID)) {
                Scanner scanner = new Scanner(System.in);
                int ch;
                do {
                    System.out.println("\n~~~~~~~~~~~~~~~~~~");
                    System.out.println(" User Option Menu");
                    System.out.println("~~~~~~~~~~~~~~~~~~");
                    System.out.println("1. Reserve\n2. Return to the main menu");
                    System.out.print("Enter your choice: ");
                    ch = scanner.nextInt();
                    System.out.println();

                    switch (ch) {
                        case 1:
                            TrainTicketReservation();
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a number from 1 to 3.");
                    }
                } while (ch <= 3);

                return;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("Please input a valid ID!");
}


    private static void userManagement() throws IOException {
        char continuation;
        RandomAccessFile file;
        Login user;
        do {
            user = new Login();
            String userID = user.getId();
            file = new RandomAccessFile("id.txt", "r");

            // Check if the user ID already exists in the file
            boolean idExists = false;
            String line;
            while ((line = file.readLine()) != null) {
                if (line.trim().equals(userID)) {
                idExists = true;
                break;
                }
            }

        file.close();

        if (idExists) {
            System.out.println("User ID already exists. Please enter a different ID.");
        } else {
            // Add the user ID to the file
            file = new RandomAccessFile("id.txt", "rw");
            file.seek(file.length());
            file.writeBytes(user.toString());
            file.close();
            System.out.println("User added successfully.");
        }

        System.out.print("Do you want to add one more record? (y-Yes/n-No): ");
        continuation = input.next().charAt(0);
        } while (continuation == 'y');
    }
    
    
    
private static void TrainTicketReservation() {
    displayAvailableTrains(TRAINS);

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter the train number you want to choose: ");
    int trainNumber = scanner.nextInt();

    TrainRecord selectedTrain = getSelectedTrain(TRAINS, trainNumber);

    if (selectedTrain != null) {
        if (selectedTrain.isFullyBooked(true) && selectedTrain.isFullyBooked(false)) {
            System.out.println("\nThe train is fully booked.");
            return;
        }

        System.out.println("\nSelected Train: " + selectedTrain.getTrainName());
        System.out.println("Boarding Point: " + selectedTrain.getBoardingStation());
        System.out.println("Destination Point: " + selectedTrain.getDestinationStation());
        System.out.println("Date: " + selectedTrain.getDate());

        System.out.print("\nEnter the number of seats to reserve: ");
        int seatQuantity = scanner.nextInt();

        if (seatQuantity <= 0) {
            System.out.println("\nInvalid seat quantity. Please enter a positive number.");
            return;
        }

        System.out.println("\nSelect the class:");
        System.out.println("1. First Class");
        System.out.println("2. Second Class");
        System.out.print("Enter your choice: ");
        int classChoice = scanner.nextInt();

        if ((classChoice == 1 && selectedTrain.isFullyBooked(true)) || (classChoice == 2 && selectedTrain.isFullyBooked(false))) {
            System.out.println("\nSeats not available for the selected class.");
            return;
        }

        Reservation reservation;
        boolean isFirstClass = classChoice == 1;
        int availableSeats = isFirstClass ? selectedTrain.getFirstClassSeats() : selectedTrain.getSecondClassSeats();

        if (seatQuantity > availableSeats) {
            System.out.println("\nNot enough seats available for the selected class or quantity.");
            return;
        }

        boolean reservationSuccess = selectedTrain.reserveSeats(seatQuantity, isFirstClass);

        if (reservationSuccess) {
            reservation = new Reservation(selectedTrain, seatQuantity, isFirstClass, 0, 0, 0, false, new ArrayList<>());
            reservation.chooseMealOption();
            reservation.calculateTotalFare();
            reservation.displayReservationDetails();
        } else {
            System.out.println("\nSeats not available for the selected class or quantity.");
        }
    } else {
        System.out.println("\nInvalid train number.");
    }

    System.out.println("\nThank you for using Train Ticket Reservation!");
}




    private static void displayAvailableTrains(TrainRecord[] trains) {
    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
    System.out.println("Available Trains:");
    System.out.println("Train No        Train Name             BoardingStation\t\tDestinationStation\tDate\t\tFirst Class Seats\tSecond Class Seats");
    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

    for (TrainRecord train : trains) {
        System.out.println(train.getTrainNo() + "\t\t" + train.getTrainName() + "\t\t" + train.getBoardingStation() + "\t\t" + train.getDestinationStation() + "\t\t\t" + train.getDate() + "\t\t" + train.getFirstClassSeats() + "\t\t\t" + train.getSecondClassSeats());
    }
}


    private static TrainRecord getSelectedTrain(TrainRecord[] trains, int trainNumber) {
        for (TrainRecord train : trains) {
            if (train.getTrainNo() == trainNumber) {
                return train;
            }
        }
        return null;
    }
}

