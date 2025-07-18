import java.util.*;

public class GameStore {
    private Authenticator authenticator;
    private MenuManager menuManager;
    private DataManager dataManager;
    private PaymentManager paymentManager;
    private User currentUser;
    private List<Game> games;
    private GamePass gamePass;  // hanya satu GamePass
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
        gamePass = dataManager.loadGamePass(); // ✅ ganti dari loadGamePasses()
        if (gamePass == null) {
            gamePass = new GamePass(0.0); // fallback jika belum ada data
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menuManager.showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> menuManager.showRegistrationMenu(scanner, this);
                case 3 -> {
                    System.out.println("Terima kasih telah menggunakan layanan kami!");
                    saveAllData();
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public boolean registerCustomer(String username, String password) {
        return dataManager.registerCustomer(username, password);
    }

    private void login(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            currentUser = authenticator.authenticate(username, password);
            if (currentUser == null) {
                System.out.println("Autentikasi gagal!");
                return;
            }

            if (currentUser instanceof Admin admin) {
                menuManager.showAdminMenu(admin, scanner, this);
            } else if (currentUser instanceof Customer customer) {
                menuManager.showCustomerMenu(customer, scanner, this);
            }
        } catch (AuthenticationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addGame(Game game) {
        games.add(game);
        dataManager.saveGames(games);
    }

    public void removeGame(String gameId) {
        games.removeIf(game -> game.getGameId().equals(gameId));
        dataManager.saveGames(games);

        // ✅ Hapus juga dari gamePass jika ada
        gamePass.removeGame(gameId);
        dataManager.saveGamePass(gamePass);
    }

    public GamePass getGamePass() {
        return gamePass;
    }

    public void setGamePassPrice(double newPrice) {
        gamePass.setPricePerMonth(newPrice); // ✅ langsung ubah harga
        dataManager.saveGamePass(gamePass);  // ✅ simpan satu gamepass
    }

    public void processTransaction(Transaction transaction) {
        transactions.add(transaction);
        dataManager.saveTransactions(new ArrayList<>(transactions));

        if (transaction.getUser() instanceof Customer customer) {
            customers.put(customer.getUsername(), customer);
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

        List<User> allUsers = new ArrayList<>(customers.values());
        for (User user : dataManager.loadUsers()) {
            if (user instanceof Admin) {
                allUsers.add(user);
            }
        }
        dataManager.saveUsers(allUsers);
    }
}
