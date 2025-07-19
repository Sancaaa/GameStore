import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataManager {
    //bikin dirextory data pada parent directory 
    private static final String BASE_PATH = "../data/"; 
    //path file csv(excel) buat data
    private static final String USERS_FILE = BASE_PATH + "users.csv";
    private static final String GAMES_FILE = BASE_PATH + "games.csv";
    private static final String GAMEPASSES_FILE = BASE_PATH + "gamepasses.csv";
    private static final String TRANSACTIONS_FILE = BASE_PATH + "transactions.csv";
    private static final String TRANSACTION_DETAILS_FILE = BASE_PATH + "transaction_details.csv";
    
    // NEW: File untuk menyimpan owned games customer
    private static final String CUSTOMER_GAMES_FILE = BASE_PATH + "customer_games.csv";
    // NEW: File untuk menyimpan status GamePass customer
    private static final String CUSTOMER_GAMEPASS_FILE = BASE_PATH + "customer_gamepass.csv";

    //constructor
    public DataManager() {
        createDataDirectory();
    }

    // buat directory data jika belum ada
    private void createDataDirectory() {
        try {
            Files.createDirectories(Paths.get(BASE_PATH));
            //kalau gagal membuat akan ada pesan error 
        } catch (IOException e) {
            System.err.println("Gagal membuat direktori data: " + e.getMessage());
        }
    }
    
    // Load data users dari csv 
    public List<User> loadUsers() {
        //buat object arraylist
        List<User> users = new ArrayList<>();
        //membuat object reaader untuk membaca file
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            //data diambil per row pada csv
            while ((line = reader.readLine()) != null) {
                //data di parse berdasarkan koma
                String[] parts = line.split(",");
                //data hanya diambil jika ada 3 atau lebih kolom (username, password, role)
                if (parts.length >= 3) {
                    //memisahkan user berdasarkan index ke 2 (role)
                    if ("Admin".equals(parts[2])) {
                        users.add(new Admin(parts[0], parts[1]));
                    } else if ("Customer".equals(parts[2])) {
                        //ubah index ke 3 (balance) jadi double  
                        Customer customer = new Customer(parts[0], parts[1], Double.parseDouble(parts[3]));
                        
                        // LOAD OWNED GAMES untuk customer ini
                        loadCustomerGames(customer);
                        
                        // LOAD GAMEPASS STATUS untuk customer ini
                        loadCustomerGamePass(customer);
                        
                        users.add(customer);
                    }
                }
            }
        // Jika file tidak ada, akan membuat file baru
        } catch (IOException e) {
            System.out.println("Database users belum ada, membuat database users...");
            try {
                new File(USERS_FILE).createNewFile();
            } catch (IOException ee) {
                System.err.println("Gagal membuat file users.csv: " + ee.getMessage());
            }   
        }
        //return list users
        return users;
    }

    //menyimpan data users ke csv
    public void saveUsers(List<User> users) {
        // membuat object writer untuk menulis ke file
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            // itterate untuk setiap user dalam list users
            for (User user : users) {
                // memisahkan data dengan koma untuk setiap kolom data 
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                //memisahkan penulisan dengan delimiter koma untuk setiap index data
                writer.println(String.join(",",
                        customer.getUsername(),
                        customer.getPassword(),
                        customer.getRole(),
                        //ubah balance jadi string
                        String.valueOf(customer.getBalance())
                ));
                
                // SIMPAN OWNED GAMES untuk customer ini
                saveCustomerGames(customer);
                
                // SIMPAN GAMEPASS STATUS untuk customer ini
                saveCustomerGamePass(customer);
                
            } else {
                writer.println(String.join(",",
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole()
                ));
                }
            }
        // Jika gagal menyimpan, akan ada pesan error
        } catch (IOException e) {
            System.err.println("Gagal menyimpan users: " + e.getMessage());
        }
    }

    // NEW METHOD: Load owned games untuk customer tertentu
    private void loadCustomerGames(Customer customer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_GAMES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(customer.getUsername())) {
                    // parts[1] adalah gameId, cari game tersebut
                    Game game = findGameById(parts[1]);
                    if (game != null) {
                        customer.addOwnedGame(game);
                    }
                }
            }
        } catch (IOException e) {
            // File belum ada, buat file kosong
            try {
                new File(CUSTOMER_GAMES_FILE).createNewFile();
            } catch (IOException ee) {
                System.err.println("Gagal membuat file customer games: " + ee.getMessage());
            }
        }
    }

    // NEW METHOD: Save owned games untuk customer tertentu
    private void saveCustomerGames(Customer customer) {
        // Pertama, hapus data lama customer ini
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_GAMES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Simpan baris yang bukan milik customer ini
                if (parts.length < 1 || !parts[0].equals(customer.getUsername())) {
                    allLines.add(line);
                }
            }
        } catch (IOException e) {
            // File belum ada, tidak apa-apa
        }

        // Tambahkan data baru customer ini
        for (Game game : customer.getOwnedGames()) {
            allLines.add(customer.getUsername() + "," + game.getGameId());
        }

        // Tulis ulang file
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_GAMES_FILE))) {
            for (String line : allLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan customer games: " + e.getMessage());
        }
    }

    // NEW METHOD: Load GamePass status untuk customer tertentu
    private void loadCustomerGamePass(Customer customer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_GAMEPASS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(customer.getUsername())) {
                    boolean isActive = Boolean.parseBoolean(parts[1]);
                    String gamePassId = parts[2];
                    
                    customer.setGamePassActive(isActive);
                    if (isActive && !gamePassId.isEmpty()) {
                        // Load GamePass berdasarkan ID
                        GamePass gamePass = loadGamePass(loadGames());
                        if (gamePass != null && gamePass.getPassId().equals(gamePassId)) {
                            customer.setActiveGamePass(gamePass);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // File belum ada, buat file kosong
            try {
                new File(CUSTOMER_GAMEPASS_FILE).createNewFile();
            } catch (IOException ee) {
                System.err.println("Gagal membuat file customer gamepass: " + ee.getMessage());
            }
        }
    }

    // NEW METHOD: Save GamePass status untuk customer tertentu
    private void saveCustomerGamePass(Customer customer) {
        // Pertama, hapus data lama customer ini
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_GAMEPASS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Simpan baris yang bukan milik customer ini
                if (parts.length < 1 || !parts[0].equals(customer.getUsername())) {
                    allLines.add(line);
                }
            }
        } catch (IOException e) {
            // File belum ada, tidak apa-apa
        }

        // Tambahkan data baru customer ini
        String gamePassId = "";
        if (customer.isGamePassActive() && customer.getActiveGamePass() != null) {
            gamePassId = customer.getActiveGamePass().getPassId();
        }
        allLines.add(customer.getUsername() + "," + customer.isGamePassActive() + "," + gamePassId);

        // Tulis ulang file
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_GAMEPASS_FILE))) {
            for (String line : allLines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan customer gamepass: " + e.getMessage());
        }
    }

    // HELPER METHOD: Cari game berdasarkan ID
    private Game findGameById(String gameId) {
        List<Game> games = loadGames();
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                return game;
            }
        }
        return null;
    }

    // Load data games dari csv
    public List<Game> loadGames() {
        //buat object arraylist
        List<Game> games = new ArrayList<>();
        //membuat object reaader untuk membaca file
        try (BufferedReader reader = new BufferedReader(new FileReader(GAMES_FILE))) {
            String line;
            //data diambil per row pada csv
            while ((line = reader.readLine()) != null) {
                //data di parse berdasarkan koma
                String[] parts = line.split(",");
                //data hanya diambil jika ada 5 kolom (gameId, title, price, isFree, genre)
                if (parts.length == 5) {
                    games.add(new Game(
                            parts[0],
                            parts[1],
                            //ubah harga dan isfree jadi double dan boolean
                            Double.parseDouble(parts[2]),
                            Boolean.parseBoolean(parts[3]),
                            parts[4]
                    ));
                }
            }
        // Jika file tidak ada, akan membuat file baru
        } catch (IOException e) {
            System.out.println("Database games belum ada, membuat database games...");
            try {
                new File(GAMES_FILE).createNewFile();
            } catch (IOException ee) {
                System.err.println("Gagal membuat database games: " + ee.getMessage());
            }   
        }
        //return list games
        return games;
    }

    //menyimpan data games ke csv
    public void saveGames(List<Game> games) {
        // membuat object writer untuk menulis ke file
        try (PrintWriter writer = new PrintWriter(new FileWriter(GAMES_FILE))) {
            // itterate untuk setiap game dalam list games
            for (Game game : games) {
                //memisahkan penulisan dengan delimiter koma untuk setiap index data
                writer.println(String.join(",",
                        game.getGameId(),
                        game.getTitle(),
                        //ubah harga dan boolean jadi string
                        String.valueOf(game.getPrice()),
                        String.valueOf(game.isFree()),
                        game.getGenre()
                ));
            }
        // Jika gagal menyimpan, akan ada pesan error
        } catch (IOException e) {
            System.err.println("Gagal menyimpan games: " + e.getMessage());
        }
    }

    // Load data gamePasses dari csv
public GamePass loadGamePass(List<Game> allGames) {
    try (BufferedReader reader = new BufferedReader(new FileReader(GAMEPASSES_FILE))) {
        String line = reader.readLine();
        if (line != null) {
            String[] parts = line.split(",");
            if (parts.length >= 1) {
                double price = Double.parseDouble(parts[0]);
                String name = (parts.length > 1) ? parts[1] : "GamePass";
                
                List<Game> includedGames = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    String gameId = parts[i];
                    for (Game game : allGames) {
                        if (game.getGameId().equals(gameId)) {
                            includedGames.add(game);
                            break;
                        }
                    }
                }
                GamePass gamePass = new GamePass("GP001", name, price, includedGames);
                return gamePass;
            }
        }
    } catch (IOException e) {
        System.out.println("Belum ada GamePass. Membuat file kosong...");
        try {
            new File(GAMEPASSES_FILE).createNewFile();
        } catch (IOException ee) {
            System.err.println("Gagal membuat file: " + ee.getMessage());
        }
    }
    return null;
}


    //menyimpan data gamePasses ke csv
public void saveGamePass(GamePass pass) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(GAMEPASSES_FILE))) {
        StringBuilder sb = new StringBuilder();
        sb.append(pass.getPricePerMonth())
          .append(",")
          .append(pass.getName());
        
        for (Game game : pass.getGamesIncluded()) {
            sb.append(",").append(game.getGameId());
        }
        
        writer.println(sb.toString());
    } catch (IOException e) {
        System.err.println("Gagal menyimpan gamepass: " + e.getMessage());
    }
}

    // Load data transaction details dari csv
    public Map<String, List<Transaction.TransactionDetail>> loadTransactionDetails() {
        //buat object hashmap untuk menyimpan detail berdasarkan transactionId
        Map<String, List<Transaction.TransactionDetail>> detailsMap = new HashMap<>();
        //membuat object reader untuk membaca file
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_DETAILS_FILE))) {
            String line;
            //data diambil per row pada csv
            while ((line = reader.readLine()) != null) {
                //data di parse berdasarkan koma
                String[] parts = line.split(",");
                //data hanya diambil jika ada 6 kolom (transactionId, itemId, itemType, quantity, price, totalPrice)
                if (parts.length >= 5) {
                    String transactionId = parts[0];
                    String itemId = parts[1];
                    String itemType = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    
                    // buat object TransactionDetail
                    Transaction.TransactionDetail detail = new Transaction.TransactionDetail(
                        itemId, itemType, quantity, price
                    );
                    
                    // masukkan detail ke map berdasarkan transactionId
                    detailsMap.computeIfAbsent(transactionId, k -> new ArrayList<>()).add(detail);
                }
            }
        // Jika file tidak ada, akan membuat file baru
        } catch (IOException | NumberFormatException e) {
            System.out.println("Database transaction details belum ada, membuat database transaction details...");
            try {
                new File(TRANSACTION_DETAILS_FILE).createNewFile();
            } catch (IOException ee) {
                // Jika gagal membuat file, akan ada pesan error
                System.err.println("Gagal membuat database transaction details: " + ee.getMessage());
            }   
        }
        //return map details
        return detailsMap;
    }

    //menyimpan data transaction details ke csv
    public void saveTransactionDetails(List<Transaction> transactions) {
        // membuat object writer untuk menulis ke file
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTION_DETAILS_FILE))) {
            // itterate untuk setiap transaction dalam list transactions
            for (Transaction trans : transactions) {
                // itterate untuk setiap detail dalam transaction
                for (Transaction.TransactionDetail detail : trans.getDetails()) {
                    //memisahkan penulisan dengan delimiter koma untuk setiap index data
                    writer.println(String.join(",",
                        trans.getTransactionId(),
                        detail.getItemId(),
                        detail.getItemType(),
                        String.valueOf(detail.getQuantity()),
                        String.valueOf(detail.getPrice())
                    ));
                }
            }
        // Jika gagal menyimpan, akan ada pesan error
        } catch (IOException e) {
            System.err.println("Gagal menyimpan transaction details: " + e.getMessage());
        }
    }

    // Load data transactions dari csv dg detail transaction
    public List<Transaction> loadTransactions() {
        //buat object arraylist
        List<Transaction> transactions = new ArrayList<>();
        //load transaction details dulu
        Map<String, List<Transaction.TransactionDetail>> detailsMap = loadTransactionDetails();

        //membuat object reaader untuk membaca file
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            //data diambil per row pada csv
            while ((line = reader.readLine()) != null) {
                //data di parse berdasarkan koma
                String[] parts = line.split(",");
                //data hanya diambil jika ada 4 kolom (transactionId, username, date, amount)
                if (parts.length >= 4) {
                    //disimpan ke dalam variabel dulu karena user harus ditemukan berdasarkan username
                    String transactionId = parts[0];
                    String username = parts[1];
                    Date date = new Date(Long.parseLong(parts[2]));
                    double amount = Double.parseDouble(parts[3]);

                    // cari user berdasarkan username
                    // jika user ditemukan, buat transaksi baru
                    // jika tidak ditemukan, transaksi tidak akan diload
                    User user = findUserByUsername(username);
                    if (user != null) {
                        // membuat objek Transaction baru
                        Transaction transaction = new Transaction(transactionId, user, amount, date);

                        // tambahkan details ke transaction jika ada
                        List<Transaction.TransactionDetail> details = detailsMap.get(transactionId);
                        if (details != null) {
                            for (Transaction.TransactionDetail detail : details) {
                                // tambahkan objek detailtransaksi ke list punya objek transaction
                                transaction.addDetail(detail);
                            }
                            // update amount dari transaction berdasarkan detail
                            transaction.updateAmountFromDetails();
                        }
                        // tambhakan transaksi ke list transactions
                        transactions.add(transaction);
                    }
                }
            }
        // Jika file tidak ada, akan membuat file baru
        } catch (IOException e) {
            System.out.println("Database transaction belum ada, membuat database transaction...");
            try {
                new File(TRANSACTIONS_FILE).createNewFile();
            // Jika gagal membuat file, akan ada pesan error
            } catch (IOException ee) {
                System.err.println("Gagal membuat database transaction: " + ee.getMessage());
            }  
        }
        //return list transactions
        return transactions;
    }

    //menyimpan data transactions ke csv
    public void saveTransactions(List<Transaction> transactions) {
        // membuat object writer untuk menulis ke file
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            // itterate untuk setiap transaction dalam list transactions
            for (Transaction trans : transactions) {
                //memisahkan penulisan dengan delimiter koma untuk setiap index data
                writer.println(String.join(",",
                    trans.getTransactionId(),
                    trans.getUser().getUsername(),
                    // ubah date dan amount jadi string
                    String.valueOf(trans.getDate().getTime()),
                    String.valueOf(trans.getAmount())
                ));
            }
        // Jika gagal menyimpan, akan ada pesan error
        } catch (IOException e) {
            System.err.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }

    // register customer baru
    // jika username sudah ada, return false
    public boolean registerCustomer(String username, String password) {
        // load users ke list users
        List<User> users = loadUsers();
        // cek apakah username sudah ada
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; 
            }
        }

        // buat customer baru dan tambahkan ke list users (balance 0)
        // kemudian simpan kembali ke file
        Customer newCustomer = new Customer(username, password, 0.0);
        users.add(newCustomer);
        saveUsers(users);
        return true;
    }

    // mencari user berdasarkan username
    // jika tidak ditemukan, return null
    private User findUserByUsername(String username) {
        // load users ke list users
        List<User> users = loadUsers();
        // cari user yang sama dengan parameter username yg diterima
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}