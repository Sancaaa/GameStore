import java.util.List;

public class Authenticator {
    //definisikan field dataManager sebagai atribut
    private DataManager dataManager;

    public Authenticator(DataManager dataManager) { this.dataManager = dataManager; }

    public User authenticate(String username, String password) throws AuthenticationException {
        List<User> users = dataManager.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new AuthenticationException("Username atau password salah");
    }
}