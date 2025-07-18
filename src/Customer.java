import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    // atribut
    private double balance;
    private boolean gamePassActive;
    private List<Game> ownedGames;
    private GamePass activeGamePass;

    // Constructor
    public Customer(String username, String password, double balance) {
        super(username, password, "Customer");
        this.balance = balance;
        this.gamePassActive = false;
        this.ownedGames = new ArrayList<>();
        this.activeGamePass = null;
    }

    // Getters 
    public double getBalance() { return balance; }
    public boolean isGamePassActive() { return gamePassActive; }
    public List<Game> getOwnedGames() { return ownedGames; }
    public GamePass getActiveGamePass() { return activeGamePass; }
    
    // Setters
    public void setBalance(double balance) { this.balance = balance; }
    public void setGamePassActive(boolean gamePassActive) { this.gamePassActive = gamePassActive; }
    public void addOwnedGame(Game game) { ownedGames.add(game); }
    public void setActiveGamePass(GamePass gamePass) { this.activeGamePass = gamePass; }

    public boolean hasGame(Game gameToCheck) {
        for (Game ownedGame : ownedGames) {
            if (ownedGame.getGameId().equals(gameToCheck.getGameId())) {
                return true;
            }
        }
        return false;
    }
}