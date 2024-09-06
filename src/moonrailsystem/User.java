package moonrailsystem;

public class User {
    private String id;
    
    public User(String userID) {
        this.id = userID;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
