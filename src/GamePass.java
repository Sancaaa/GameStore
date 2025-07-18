import java.util.ArrayList;
import java.util.List;

public class GamePass { 
    // Atribut
    private String name;
    private double pricePerMonth;
    private List<Game> gamesIncluded;

    // Constructor
    public GamePass(String name, double pricePerMonth) {
        this.name = name;
        this.pricePerMonth = pricePerMonth;
        this.gamesIncluded = new ArrayList<>();
    }
    // Constructor (overloaded)
    public GamePass(double pricePerMonth, List<Game> games) {
        this.pricePerMonth = pricePerMonth;
        this.gamesIncluded = games;
    }

    // Getters
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
