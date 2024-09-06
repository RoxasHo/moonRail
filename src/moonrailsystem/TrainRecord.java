package moonrailsystem;

public class TrainRecord {
    private int trainNo;
    private String trainName;
    private String boardingStation;
    private String destinationStation;
    private int firstClassSeats;
    private double firstClassFare;
    private int secondClassSeats;
    private double secondClassFare;
    private String date;

    public TrainRecord(int trainNo, String trainName, String boardingStation, String destinationStation, int firstClassSeats, double firstClassFare, int secondClassSeats, double secondClassFare, String date) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.boardingStation = boardingStation;
        this.destinationStation = destinationStation;
        this.firstClassSeats = firstClassSeats;
        this.firstClassFare = firstClassFare;
        this.secondClassSeats = secondClassSeats;
        this.secondClassFare = secondClassFare;
        this.date = date;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getBoardingStation() {
        return boardingStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public double getFirstClassFare() {
        return firstClassFare;
    }

    public int getSecondClassSeats() {
        return secondClassSeats;
    }

    public double getSecondClassFare() {
        return secondClassFare;
    }

    public String getDate() {
        return date;
    }
    
    public boolean reserveSeats(int seatQuantity, boolean isFirstClass) {
    int availableSeats = isFirstClass ? firstClassSeats : secondClassSeats;
    
    if (availableSeats >= seatQuantity) {
        if (isFirstClass) {
            firstClassSeats -= seatQuantity;
        } else {
            secondClassSeats -= seatQuantity;
        }
        
        return true; // Reservation successful
    } else {
        return false; // Not enough seats available
    }
}

    
    public boolean isFullyBooked(boolean isFirstClass) {
    int availableSeats = isFirstClass ? firstClassSeats : secondClassSeats;
    return availableSeats == 0;
}

    
    @Override
    public String toString() {
        return String.format("%-10d\t%-10s\t%-16s\t%-20s\t%-19d\t%-16.2f\t%-18d\t%-18f\t%s", trainNo, trainName, boardingStation, destinationStation, firstClassSeats, firstClassFare, secondClassSeats, secondClassFare, date);
    }

   
}
