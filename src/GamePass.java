import java.util.ArrayList;
import java.util.List;

public class GamePass { 
    private String passId;  // ✅ Tambahkan field ini
    private String name;
    private double pricePerMonth;
    private List<Game> gamesIncluded;

    // Constructor
    public GamePass(String passId, String name, double pricePerMonth) {
        this.passId = passId;
        this.name = name;
        this.pricePerMonth = pricePerMonth;
        this.gamesIncluded = new ArrayList<>();
    }
    
    // Constructor (overloaded)
    public GamePass(String passId, String name, double pricePerMonth, List<Game> games) {
        this.passId = passId;
        this.name = name;
        this.pricePerMonth = pricePerMonth;
        this.gamesIncluded = games;
    }

    // Getters
    public String getPassId() { return passId; }
    public String getName() { return name; }
    public double getPricePerMonth() { return pricePerMonth; }
    public List<Game> getGamesIncluded() { return gamesIncluded; }

    // Setters
    public void setPricePerMonth(double price) { this.pricePerMonth = price; }

    // Method untuk menambah dan menghapus game
    public void addGame(Game game) { this.gamesIncluded.add(game);}
    public void removeGame(Game game) { this.gamesIncluded.remove(game); }
    public void removeGame(String gameId) {
        gamesIncluded.removeIf(game -> game.getGameId().equals(gameId));
    }
}
