package moonrailsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Reservation {
    
    private TrainRecord train;
    private int seatQuantity;
    private boolean isFirstClass;
    private double totalFare;
    private double subtotalFare;
    private double mealFare;
    private boolean mealOption;
    private List<MealItem> selectedMeals;


    public Reservation(TrainRecord train, int seatQuantity, boolean isFirstClass, double totalFare, double subtotalFare, double mealFare, boolean mealOption, List<MealItem> selectedMeals) {
        this.train = train;
        this.seatQuantity = seatQuantity;
        this.isFirstClass = isFirstClass;
        this.totalFare = totalFare;
        this.subtotalFare = subtotalFare;
        this.mealFare = mealFare;
        this.mealOption = mealOption;
        this.selectedMeals = selectedMeals;
    }

    public double getSubtotalFare() {
        return subtotalFare;
    }

    public void setSubtotalFare(double subtotalFare) {
        this.subtotalFare = subtotalFare;
    }

    public double getMealFare() {
        return mealFare;
    }

    public void setMealFare(double mealFare) {
        this.mealFare = mealFare;
    }
    
    public boolean isMealOption() {
        return mealOption;
    }
    

    public TrainRecord getTrain() {
        return train;
    }

    public void setMealOption(boolean mealOption) {
        this.mealOption = mealOption;
    }

    public List<MealItem> getSelectedMeals() {
        return selectedMeals;
    }

    public void setSelectedMeals(List<MealItem> selectedMeals) {
        this.selectedMeals = selectedMeals;
    }

    public void setTrain(TrainRecord train) {
        this.train = train;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public boolean isIsFirstClass() {
        return isFirstClass;
    }

    public void setIsFirstClass(boolean isFirstClass) {
        this.isFirstClass = isFirstClass;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
        }
    
    public void calculateTotalFare() {
        if (isFirstClass) {
            subtotalFare = train.getFirstClassFare() * seatQuantity;
            if (mealOption) {
                for (MealItem mealItem : selectedMeals) {
                    mealFare += mealItem.getPrice() * mealItem.getQuantity();
                }
            }   
        } else {
            subtotalFare = train.getSecondClassFare() * seatQuantity;
        }
        totalFare = subtotalFare + mealFare;
    }
    

public void displayReservationDetails() {
    if (isFirstClass && mealOption) {
        System.out.println("Selected Meals:");
        for (MealItem mealItem : selectedMeals) {
            System.out.println("Item: " + mealItem.getName() + " | Quantity: " + mealItem.getQuantity());
        }
    }

    System.out.println("\nReservation Successful!");
    System.out.println("\n\nTicket Details:");
    System.out.println("Train: " + train.getTrainName());
    System.out.println("Train No: " + train.getTrainNo());
    System.out.println("Boarding Station: " + train.getBoardingStation());
    System.out.println("Destination Station: " + train.getDestinationStation());
    System.out.println("Date: " + train.getDate());
    System.out.println("Class: " + (isFirstClass ? "First Class" : "Second Class"));
    System.out.println("Total Seats: " + seatQuantity);
    System.out.println("Ticket Fare: " + subtotalFare);
    System.out.println("Meal Fare: " + mealFare);
    System.out.println("Total Fare: $" + totalFare);
    System.out.println("\nPlease Print out This Ticket And Pay at the Counter, Enjoy Your Trip!");
    System.out.println("Yours Sincerely, MoonRails.");

    // Store customer-relevant reservation details in a text file
    try (PrintWriter writer = new PrintWriter(new FileWriter("reservation.txt", true))) {
        writer.println("Ticket Details:");
        writer.println("Train: " + train.getTrainName());
        writer.println("Train No: " + train.getTrainNo());
        writer.println("Boarding Station: " + train.getBoardingStation());
        writer.println("Destination Station: " + train.getDestinationStation());
        writer.println("Date: " + train.getDate());
        writer.println("Class: " + (isFirstClass ? "First Class" : "Second Class"));
        writer.println("Total Seats: " + seatQuantity);
        writer.println("Ticket Fare: " + subtotalFare);
        writer.println("Meal Fare: " + mealFare);
        writer.println("Total Fare: $" + totalFare);
        writer.println("------------------------------------------");
        if (isFirstClass && mealOption) {
            writer.println("Selected Meals:");
            for (MealItem mealItem : selectedMeals) {
                writer.println("Item: " + mealItem.getName() + " | Quantity: " + mealItem.getQuantity());
            }
            writer.println("------------------------------------------\n\n\n");
        }
        writer.flush(); // Flush the writer to ensure the data is written immediately
    } catch (IOException e) {
        System.out.println("Error occurred while storing reservation details.");
    }
}


    
    public void chooseMealOption() {
        if (isFirstClass) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do You Like To Have Meals Included? (y-Yes/n-No): ");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("y")) {
                mealOption = true;
                System.out.println("\nAvailable Meal Options:");
                System.out.println("1. Vegetarian Set");
                System.out.println("2. Normal Set");

                System.out.print("Enter your choice: ");
                int mealChoice = scanner.nextInt();
                switch (mealChoice) {
                case 1:
                    selectMealOption(MealOption.VEGETARIAN);
                    break;
                case 2:
                    selectMealOption(MealOption.MEAT);
                    break;
                default:
                    System.out.println("Invalid choice. Please Select from the Existing Options");
                }
            }
        }
    }
    
    private void selectMealOption(MealOption mealOption) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter The Amount of Meal Set You Want: ");
        int quantity = scanner.nextInt();

        MealItem mealItem = new MealItem(mealOption.getName(), mealOption.getPrice(), quantity);
        selectedMeals.add(mealItem);
    }
}