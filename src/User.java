public abstract class User {
    // attributes
    private String username;
    private String password;
    private String role;

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}