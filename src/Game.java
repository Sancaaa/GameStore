public class Game {
    // attribut
    private String gameId;
    private String title;
    private double price;
    private boolean isFree;
    private String genre;

    // Constructor
    public Game(String gameId, String title, double price, boolean isFree, String genre) {
        this.gameId = gameId;
        this.title = title;
        this.price = price;
        this.isFree = isFree;
        this.genre = genre;
    }

    // Getters
    public String getGameId() { return gameId; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public boolean isFree() { return isFree; }
    public String getGenre() { return genre; }
}