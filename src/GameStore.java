import java.util.*;

public class GameStore {
    private Authenticator authenticator;
    private MenuManager menuManager;
    private DataManager dataManager;
    private PaymentManager paymentManager;
    private User currentUser;
    private List<Game> games;
    private GamePass gamePass;  
    private Map<String, Customer> customers;
    private Set<Transaction> transactions;

    public GameStore() {
        this.dataManager = new DataManager();
        this.authenticator = new Authenticator(dataManager);
        this.paymentManager = new PaymentManager();
        this.menuManager = new MenuManager();
        loadInitialData();
    }

    private void loadInitialData() {
        games = dataManager.loadGames();
        gamePass = dataManager.loadGamePass(games);
        if (gamePass == null) {
            gamePass = new GamePass("GP001", "Default GamePass", 0.0); // jika belum ada data
        }

        transactions = new HashSet<>(dataManager.loadTransactions());
        customers = new HashMap<>();

        for (User user : dataManager.loadUsers()) {
            if (user instanceof Customer) {
                customers.put(user.getUsername(), (Customer) user);
            }
        }
    }

    public void run() {
        while (true) {
            menuManager.showMainMenu(this);
        }
    }

    public boolean registerCustomer(String username, String password) {
        boolean result = dataManager.registerCustomer(username, password);
        if (result) {
            // Reload customers after registration
            loadCustomersData();
        }
        return result;
    }

    // NEW METHOD: Save individual customer data
    public void saveCustomerData(Customer customer) {
        // Update the customer in our local map
        customers.put(customer.getUsername(), customer);
        
        // Save all users (including the updated customer) to CSV
        List<User> allUsers = new ArrayList<>();
        
        // Add all customers
        allUsers.addAll(customers.values());
        
        // Add all admins
        for (User user : dataManager.loadUsers()) {
            if (user instanceof Admin) {
                allUsers.add(user);
            }
        }
        
        dataManager.saveUsers(allUsers);
    }

    // NEW METHOD: Reload customers from data
    private void loadCustomersData() {
        customers.clear();
        for (User user : dataManager.loadUsers()) {
            if (user instanceof Customer) {
                customers.put(user.getUsername(), (Customer) user);
            }
        }
    }

    public void addGame(Game game) {
        games.add(game);
        dataManager.saveGames(games);
    }

public void removeGame(String gameId) {
    // Hapus dari games list
    games.removeIf(game -> game.getGameId().equals(gameId));
    
    // Hapus dari gamePass jika ada
    if (gamePass != null) {
        gamePass.removeGame(gameId);
        dataManager.saveGamePass(gamePass);
    }
    
    // Simpan perubahan games
    dataManager.saveGames(games);
}

    public void setGamePassPrice(double newPrice) {
        gamePass.setPricePerMonth(newPrice); // langsung ubah harga
        dataManager.saveGamePass(gamePass);  //  simpan satu gamepass
    }

    public void processTransaction(Transaction transaction) {
        transactions.add(transaction);
        dataManager.saveTransactions(new ArrayList<>(transactions));
        dataManager.saveTransactionDetails(new ArrayList<>(transactions));

        if (transaction.getUser() instanceof Customer customer) {
            customers.put(customer.getUsername(), customer);
            // Save customer data immediately after transaction
            saveCustomerData(customer);
        }
    }

    public List<Game> getGamesCatalog() {
        return new ArrayList<>(games);
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    private void saveAllData() {
        dataManager.saveGames(games);
        dataManager.saveGamePass(gamePass);
        dataManager.saveTransactions(new ArrayList<>(transactions));
        dataManager.saveTransactionDetails(new ArrayList<>(transactions));

        List<User> allUsers = new ArrayList<>(customers.values());
        for (User user : dataManager.loadUsers()) {
            if (user instanceof Admin) {
                allUsers.add(user);
            }
        }
        dataManager.saveUsers(allUsers);
    }

        public void addGameToPass(Game game) {
        if (gamePass != null) {
            gamePass.addGame(game);
            dataManager.saveGamePass(gamePass);
        }
    }
    
    public void removeGameFromPass(String gameId) {
        if (gamePass != null) {
            gamePass.removeGame(gameId);
            dataManager.saveGamePass(gamePass);
        }
    }
    
    public void updateGamePassPrice(double newPrice) {
        if (gamePass != null) {
            gamePass.setPricePerMonth(newPrice);
            dataManager.saveGamePass(gamePass);
        }
    }

    public GamePass getGamePass() {
        return gamePass;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

}