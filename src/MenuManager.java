import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner = new Scanner(System.in);
    // Helper methods for input validation
    private int readInt(String errorMessage) {
        
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
    }

    private int readIntInRange(int min, int max, String errorMessage) {
        while (true) {
            int value = Integer.parseInt(scanner.nextLine());
            if (value >= min && value <= max) {
                return value;
            }
            System.out.print("Pilihan tidak valid! " + errorMessage);
        }
    }

    private double readDouble(String errorMessage) {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    System.out.print("Nilai tidak boleh negatif! ");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
    }

    private String readNonEmptyString(String errorMessage) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print(errorMessage);
        }
    }

    private boolean readBoolean(String errorMessage) {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("ya") || input.equals("y") || input.equals("true")) {
                return true;
            } else if (input.equals("tidak") || input.equals("t") || input.equals("false")) {
                return false;
            }
            System.out.print(errorMessage);
        }
    }

    private void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    private void printSeparator() {
        System.out.println("===============================================");
    }

    private void printHeader(String title) {
        printSeparator();
        System.out.println("=== " + title + " ===");
        printSeparator();
    }

    private void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();
    }

    public void showExitMenu() {
        clearScreen();
        
        System.out.println("===========================================");
        System.out.println("|          TERIMA KASIH TELAH             |");
        System.out.println("|         MENGGUNAKAN GAMESTORE           |");
        System.out.println("===========================================");
        System.out.println("Sampai jumpa lagi!");

        System.exit(0);
    }

    public void showMainMenu(GameStore gameStore) {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        clearScreen();
        printHeader("Game Store Menu");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Keluar");
        printSeparator();
        System.out.print("Pilih opsi (1-3): ");
        
        int choice = readIntInRange(1, 3, "Pilih opsi (1-3): ");
        
        switch (choice) {
            case 1: 
                User loggedInUser = showLoginMenu(gameStore);
                    
                // Periksa apakah yang login adalah Customer
                if (loggedInUser instanceof Customer customer) {
                    showCustomerMenu(customer, gameStore);
                } else if (loggedInUser instanceof Admin admin) {
                    // Handle admin jika perlu
                    showAdminMenu(admin, gameStore);
                } else {
                    System.out.println("Login gagal! Periksa username dan password Anda.");
                }
                break;
            case 2:
                showRegistrationMenu(gameStore);
                break;
            case 3:
                showExitMenu();
            }
        }
    }

    public void showAdminMenu(Admin admin, GameStore gameStore) {
        while (true) {
            clearScreen();
            printHeader("Admin Menu");
            System.out.println("1. Tambah Game");
            System.out.println("2. Hapus Game");
            System.out.println("3. Tambah Game ke GamePass");
            System.out.println("4. Hapus Game dari GamePass");
            System.out.println("5. Update Harga GamePass");
            System.out.println("6. Lihat list Game");
            System.out.println("7. Keluar");
            printSeparator();
            System.out.print("Pilih opsi (1-7): ");

            int choice = readIntInRange(1, 7, "Pilih opsi (1-7): ");

            switch (choice) {
                case 1:
                    addGameMenu(gameStore);
                    break;
                case 2:
                    removeGameMenu(gameStore);
                    break;
                case 3:
                    addGameToPassMenu(gameStore);
                    break;
                case 4:
                    removeGameFromPassMenu(gameStore);
                    break;
                case 5:
                    updateGamePassPriceMenu(gameStore);
                    break;
                case 6:
                    showGameCatalog(gameStore.getGamesCatalog(), scanner);
                    break;
                case 7:
                    showExitMenu();
            }
        }
    }

    private void addGameMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Tambah Game Baru");
        
        System.out.print("ID Game: ");
        String gameId = readNonEmptyString("ID Game tidak boleh kosong: ");
        
        System.out.print("Judul: ");
        String title = readNonEmptyString("Judul tidak boleh kosong: ");
        
        System.out.print("Harga: ");
        double price = readDouble("Harga harus angka: ");
        
        System.out.print("Gratis? (ya/tidak): ");
        boolean isFree = readBoolean("Masukkan ya/tidak: ");
        
        System.out.print("Genre: ");
        String genre = readNonEmptyString("Genre tidak boleh kosong: ");

        Game newGame = new Game(gameId, title, price, isFree, genre);
        gameStore.addGame(newGame);
        
        System.out.println("\nGame berhasil ditambahkan!");
        pressEnterToContinue(scanner);
    }

    private void removeGameMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Hapus Game");
        
        System.out.print("Masukkan ID Game yang akan dihapus: ");
        String gameId = readNonEmptyString("ID Game tidak boleh kosong: ");
        
        if (findGameInCatalog(gameStore, gameId) == null) {
            System.out.println("\nGame tidak ditemukan!");
        } else {
            gameStore.removeGame(gameId);
            System.out.println("\nGame berhasil dihapus!");
        }
        pressEnterToContinue(scanner);
    }

    private void addGameToPassMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Tambah Game ke GamePass");
        
        List<Game> games = gameStore.getGamesCatalog();
        if (games.isEmpty()) {
            System.out.println("Belum ada game dalam katalog!");
            pressEnterToContinue(scanner);
            return;
        }
        
        System.out.println("Daftar Game Tersedia:");
        for (Game game : games) {
            System.out.printf("ID: %s | Judul: %s | Genre: %s%n", 
                game.getGameId(), game.getTitle(), game.getGenre());
        }
        printSeparator();
        
        System.out.print("Masukkan ID Game yang akan ditambahkan: ");
        String gameId = readNonEmptyString("ID Game tidak boleh kosong: ");
        
        Game selectedGame = findGameInCatalog(gameStore, gameId);
        if (selectedGame == null) {
            System.out.println("\nGame tidak ditemukan!");
        } else {
            gameStore.addGameToPass(selectedGame);
            System.out.println("\nGame berhasil ditambahkan ke GamePass!");
        }
        pressEnterToContinue(scanner);
    }

    private void removeGameFromPassMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Hapus Game dari GamePass");
        
        GamePass gamePass = gameStore.getGamePass();
        if (gamePass == null || gamePass.getGamesIncluded().isEmpty()) {
            System.out.println("GamePass kosong!");
            pressEnterToContinue(scanner);
            return;
        }
        
        System.out.println("Daftar Game dalam GamePass:");
        for (Game game : gamePass.getGamesIncluded()) {
            System.out.printf("ID: %s | Judul: %s%n", game.getGameId(), game.getTitle());
        }
        printSeparator();
        
        System.out.print("Masukkan ID Game yang akan dihapus: ");
        String gameId = readNonEmptyString("ID Game tidak boleh kosong: ");
        
        if (findGameInGamePass(gamePass, gameId) == null) {
            System.out.println("\nGame tidak ditemukan dalam GamePass!");
        } else {
            gameStore.removeGameFromPass(gameId);
            System.out.println("\nGame berhasil dihapus dari GamePass!");
        }
        pressEnterToContinue(scanner);
    }

    private void updateGamePassPriceMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Update Harga GamePass");
        
        GamePass gamePass = gameStore.getGamePass();
        if (gamePass == null) {
            System.out.println("GamePass belum tersedia!");
            pressEnterToContinue(scanner);
            return;
        }
        
        System.out.printf("Harga GamePass saat ini: %.2f%n", gamePass.getPricePerMonth());
        System.out.print("Masukkan harga baru per bulan: ");
        double newPrice = readDouble("Harga harus angka: ");
        
        gameStore.updateGamePassPrice(newPrice);
        System.out.println("\nHarga GamePass berhasil diupdate!");
        pressEnterToContinue(scanner);
    }

    public void showRegistrationMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Registrasi");
        
        System.out.print("Username: ");
        String username = readNonEmptyString("Username tidak boleh kosong: ");
        
        System.out.print("Password: ");
        String password = readNonEmptyString("Password tidak boleh kosong: ");

        if (gameStore.registerCustomer(username, password)) {
            System.out.println("\nRegistrasi berhasil! Silakan login.");
        } else {
            System.out.println("\nUsername sudah digunakan. Silakan coba username lain.");
        }
        pressEnterToContinue(scanner);
    }

    public User showLoginMenu(GameStore gameStore) {
        clearScreen();
        printHeader("Login");
        
        // Input username
        System.out.print("Username: ");
        String username = readNonEmptyString("Username tidak boleh kosong: ");
        
        // Input password
        System.out.print("Password: ");
        String password = readNonEmptyString("Password tidak boleh kosong: ");
        
        try {
            // Proses autentikasi
            User user = gameStore.getAuthenticator().authenticate(username, password);
        
            if (user == null) {
                System.out.println("\nUsername atau password salah!");
                pressEnterToContinue(scanner);
                return null;
            }
        
            if (user instanceof Admin) {
                System.out.println("\nLogin admin berhasil!");
                pressEnterToContinue(scanner);
                return user;  // Return Admin object instead of null
            } else if (user instanceof Customer) {
                System.out.println("\nLogin berhasil! Selamat datang " + user.getUsername());
                pressEnterToContinue(scanner);
                return user;
            }
        } catch (AuthenticationException e) {
            System.out.println("\nError saat login: " + e.getMessage());
            pressEnterToContinue(scanner);
        }
    
        return null;
    }

    public void showCustomerMenu(Customer customer, GameStore gameStore) {
        while (true) {
            clearScreen();
            printHeader("Customer Menu - " + customer.getUsername());
            System.out.printf("Saldo: %.2f%n", customer.getBalance());
            printSeparator();
            System.out.println("1. Lihat Katalog Game");
            System.out.println("2. Beli Game");
            System.out.println("3. Berlangganan GamePass");
            System.out.println("4. Lihat Game Saya");
            System.out.println("5. Top Up Saldo");
            System.out.println("6. Keluar");
            printSeparator();
            System.out.print("Pilih opsi (1-6): ");

            int choice = readIntInRange(1, 6, "Pilih opsi (1-6): ");

            switch (choice) {
                case 1:
                    showGameCatalog(gameStore.getGamesCatalog(), scanner);
                    break;
                case 2:
                    buyGameMenu(customer, gameStore);
                    break;
                case 3:
                    subscribeGamePassMenu(customer, gameStore);
                    break;
                case 4:
                    showMyGames(customer, scanner);
                    break;
                case 5:
                    topUpBalanceMenu(customer, gameStore);
                    break;
                case 6:
                    showExitMenu();
            }
        }
    }

    private void showGameCatalog(List<Game> games, Scanner scanner) {
        clearScreen();
        printHeader("Katalog Game");
        
        if (games.isEmpty()) {
            System.out.println("Belum ada game dalam katalog.");
        } else {
            for (Game game : games) {
                System.out.printf(
                    "ID: %s | Judul: %-20s | Harga: %8.2f | Gratis: %-5s | Genre: %s%n",
                    game.getGameId(),
                    game.getTitle(),
                    game.getPrice(),
                    game.isFree() ? "Ya" : "Tidak",
                    game.getGenre()
                );
            }
        }
        printSeparator();
        pressEnterToContinue(scanner);
    }

    private void buyGameMenu(Customer customer, GameStore gameStore) {
        clearScreen();
        printHeader("Beli Game");
        
        System.out.print("Masukkan ID Game yang ingin dibeli: ");
        String gameId = readNonEmptyString("ID Game tidak boleh kosong: ");

        Game selectedGame = findGameInCatalog(gameStore, gameId);
        if (selectedGame == null) {
            System.out.println("\nGame tidak ditemukan!");
            pressEnterToContinue(scanner);
            return;
        }

        if (customer.hasGame(selectedGame)) {
            System.out.println("\nAnda sudah memiliki game ini!");
            pressEnterToContinue(scanner);
            return;
        }

        if (selectedGame.isFree()) {
            customer.addOwnedGame(selectedGame);
            // SAVE CUSTOMER DATA AFTER FREE GAME PURCHASE
            gameStore.saveCustomerData(customer);
            System.out.println("\nGame gratis berhasil ditambahkan!");
            pressEnterToContinue(scanner);
            return;
        }

        System.out.printf("Harga game: %.2f%n", selectedGame.getPrice());
        System.out.printf("Saldo Anda: %.2f%n", customer.getBalance());
        System.out.print("Konfirmasi pembelian? (ya/tidak): ");
        boolean confirm = readBoolean("Masukkan ya/tidak: ");
        
        if (!confirm) {
            System.out.println("\nPembelian dibatalkan.");
            pressEnterToContinue(scanner);
            return;
        }

        if (gameStore.getPaymentManager().processPayment(customer, selectedGame.getPrice())) {
            customer.addOwnedGame(selectedGame);
            Transaction transaction = new Transaction(
                "TXN" + System.currentTimeMillis(),
                customer,
                selectedGame.getPrice(),
                new Date()
            );
            transaction.addDetail(new Transaction.TransactionDetail(
                    selectedGame.getGameId(),
                    "GAME",
                    1,
                    selectedGame.getPrice()
            ));
            gameStore.processTransaction(transaction);
            // SAVE CUSTOMER DATA AFTER PURCHASE
            gameStore.saveCustomerData(customer);
            System.out.println("\nPembelian berhasil!");
        } else {
            System.out.println("\nSaldo tidak mencukupi!");
        }
        pressEnterToContinue(scanner);
    }

    private void subscribeGamePassMenu(Customer customer, GameStore gameStore) {
        clearScreen();
        printHeader("Berlangganan GamePass");
        
        GamePass gamePass = gameStore.getGamePass();
        if (gamePass == null) {
            System.out.println("Belum ada GamePass yang tersedia.");
            pressEnterToContinue(scanner);
            return;
        }

        if (customer.isGamePassActive()) {
            System.out.println("Anda sudah berlangganan GamePass!");
            pressEnterToContinue(scanner);
            return;
        }

        System.out.println("GamePass Tersedia:");
        System.out.printf("Nama: %s%n", gamePass.getName());
        System.out.printf("Harga/bulan: %.2f%n", gamePass.getPricePerMonth());
        System.out.printf("Saldo Anda: %.2f%n", customer.getBalance());
        
        if (!gamePass.getGamesIncluded().isEmpty()) {
            System.out.println("Game yang Termasuk:");
            for (Game game : gamePass.getGamesIncluded()) {
                System.out.printf("- %s (%s)%n", game.getTitle(), game.getGenre());
            }
        } else {
            System.out.println("Belum ada game dalam GamePass.");
        }
        
        System.out.print("\nBerlangganan? (ya/tidak): ");
        boolean confirm = readBoolean("Masukkan ya/tidak: ");
        
        if (!confirm) {
            System.out.println("\nBerlangganan dibatalkan.");
            pressEnterToContinue(scanner);
            return;
        }

        if (gameStore.getPaymentManager().processPayment(customer, gamePass.getPricePerMonth())) {
            customer.setGamePassActive(true);
            customer.setActiveGamePass(gamePass);
            
            Transaction transaction = new Transaction(
                "TXN" + System.currentTimeMillis(),
                customer,
                gamePass.getPricePerMonth(),
                new Date()
            );
            transaction.addDetail(new Transaction.TransactionDetail(
                gamePass.getPassId(),
                "GAMEPASS",
                1,
                gamePass.getPricePerMonth()
            ));
            gameStore.processTransaction(transaction);
            // SAVE CUSTOMER DATA AFTER GAMEPASS SUBSCRIPTION
            gameStore.saveCustomerData(customer);
            
            System.out.println("\nBerlangganan berhasil!");
        } else {
            System.out.println("\nSaldo tidak mencukupi!");
        }
        pressEnterToContinue(scanner);
    }

    private void showMyGames(Customer customer, Scanner scanner) {
        clearScreen();
        printHeader("Game Saya");
        
        System.out.println("Daftar Game yang Dimiliki:");
        if (customer.getOwnedGames().isEmpty()) {
            System.out.println("Belum memiliki game.");
        } else {
            for (Game game : customer.getOwnedGames()) {
                System.out.printf(
                    "ID: %s | Judul: %-20s | Genre: %s%n",
                    game.getGameId(),
                    game.getTitle(),
                    game.getGenre()
                );
            }
        }

        if (customer.isGamePassActive() && customer.getActiveGamePass() != null) {
            System.out.println("\nGamePass Aktif:");
            System.out.println("Nama: " + customer.getActiveGamePass().getName());
            System.out.printf("Harga/bulan: %.2f%n", customer.getActiveGamePass().getPricePerMonth());
            
            if (!customer.getActiveGamePass().getGamesIncluded().isEmpty()) {
                System.out.println("Game yang Dapat Diakses:");
                for (Game game : customer.getActiveGamePass().getGamesIncluded()) {
                    System.out.printf(" - %s (%s)%n", game.getTitle(), game.getGenre());
                }
            } else {
                System.out.println("Belum ada game dalam GamePass.");
            }
        } else {
            System.out.println("\nTidak ada GamePass aktif.");
        }
        
        printSeparator();
        pressEnterToContinue(scanner);
    }

    private void topUpBalanceMenu(Customer customer, GameStore gameStore) {
        clearScreen();
        printHeader("Top Up Saldo");
        
        System.out.printf("Saldo saat ini: %.2f%n", customer.getBalance());
        System.out.print("Masukkan jumlah top up: ");
        double amount = readDouble("Jumlah harus angka positif: ");
        
        customer.setBalance(customer.getBalance() + amount);
        // SAVE CUSTOMER DATA AFTER TOP UP
        gameStore.saveCustomerData(customer);
        System.out.printf("\nSaldo berhasil ditambahkan! Saldo sekarang: %.2f%n", customer.getBalance());
        pressEnterToContinue(scanner);
    }

    private Game findGameInCatalog(GameStore gameStore, String gameId) {
    for (Game game : gameStore.getGamesCatalog()) {
        if (game.getGameId().equals(gameId)) {
            return game;
        }
    }
    return null;
}

private Game findGameInGamePass(GamePass gamePass, String gameId) {
    if (gamePass == null) return null;
    for (Game game : gamePass.getGamesIncluded()) {
        if (game.getGameId().equals(gameId)) {
            return game;
        }
    }
    return null;
}

}