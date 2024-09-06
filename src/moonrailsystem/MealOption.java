package moonrailsystem;

public enum MealOption {
    VEGETARIAN("Vegetarian Diet", 10.0),
    MEAT("Meat", 15.0);

    private String name;
    private double price;

    MealOption(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}