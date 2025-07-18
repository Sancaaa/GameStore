import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
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
            // Fallback for any errors
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

    public void showMainMenu() {
        clearScreen();
        printHeader("Game Store Menu");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Keluar");
        printSeparator();
        System.out.print("Pilih opsi: ");
    }

    public void showAdminMenu(Admin admin, Scanner scanner, GameStore gameStore) {
        while (true) {
            clearScreen();
            printHeader("Admin Menu");
            System.out.println("1. Tambah Game");
            System.out.println("2. Hapus Game");
            System.out.println("3. Tambah GamePass");
            System.out.println("4. Hapus GamePass");
            System.out.println("5. Keluar");
            printSeparator();
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addGameMenu(scanner, gameStore);
                    break;
                case 2:
                    removeGameMenu(scanner, gameStore);
                    break;
                case 3:
                    addGamePassMenu(scanner, gameStore);
                    break;
                case 4:
                    removeGamePassMenu(scanner, gameStore);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
                    pressEnterToContinue(scanner);
            }
        }
    }

    private void addGameMenu(Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Tambah Game Baru");
        
        System.out.print("ID Game: ");
        String gameId = scanner.nextLine();
        System.out.print("Judul: ");
        String title = scanner.nextLine();
        System.out.print("Harga: ");
        double price = scanner.nextDouble();
        System.out.print("Gratis? (true/false): ");
        boolean isFree = scanner.nextBoolean();
        scanner.nextLine();  // Consume newline
        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        Game newGame = new Game(gameId, title, price, isFree, genre);
        gameStore.addGame(newGame);
        
        System.out.println("\nGame berhasil ditambahkan!");
        pressEnterToContinue(scanner);
    }

    private void removeGameMenu(Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Hapus Game");
        
        System.out.print("Masukkan ID Game yang akan dihapus: ");
        String gameId = scanner.nextLine();
        gameStore.removeGame(gameId);
        
        System.out.println("\nGame berhasil dihapus!");
        pressEnterToContinue(scanner);
    }

    private void addGamePassMenu(Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Tambah GamePass Baru");
        
        System.out.print("ID GamePass: ");
        String passId = scanner.nextLine();
        System.out.print("Nama: ");
        String name = scanner.nextLine();
        System.out.print("Harga per Bulan: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        GamePass newGamePass = new GamePass(passId, name, price);
        gameStore.addGamePass(newGamePass);
        
        System.out.println("\nGamePass berhasil ditambahkan!");
        pressEnterToContinue(scanner);
    }

    private void removeGamePassMenu(Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Hapus GamePass");
        
        System.out.print("Masukkan ID GamePass yang akan dihapus: ");
        String passId = scanner.nextLine();
        gameStore.removeGamePass(passId);
        
        System.out.println("\nGamePass berhasil dihapus!");
        pressEnterToContinue(scanner);
    }

    public void showRegistrationMenu(Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Registrasi");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (gameStore.registerCustomer(username, password)) {
            System.out.println("\nRegistrasi berhasil! Silakan login.");
        } else {
            System.out.println("\nUsername sudah digunakan. Silakan coba username lain.");
        }
        pressEnterToContinue(scanner);
    }

    public void showCustomerMenu(Customer customer, Scanner scanner, GameStore gameStore) {
        while (true) {
            clearScreen();
            printHeader("Customer Menu");
            System.out.println("1. Lihat Katalog Game");
            System.out.println("2. Beli Game");
            System.out.println("3. Berlangganan GamePass");
            System.out.println("4. Lihat Game Saya");
            System.out.println("5. Top Up Saldo");
            System.out.println("6. Keluar");
            printSeparator();
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    showGameCatalog(gameStore.getGamesCatalog(), scanner);
                    break;
                case 2:
                    buyGameMenu(customer, scanner, gameStore);
                    break;
                case 3:
                    subscribeGamePassMenu(customer, scanner, gameStore);
                    break;
                case 4:
                    showMyGames(customer, scanner);
                    break;
                case 5:
                    topUpBalanceMenu(customer, scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
                    pressEnterToContinue(scanner);
            }
        }
    }

    private void showGameCatalog(List<Game> games, Scanner scanner) {
        clearScreen();
        printHeader("Katalog Game");
        
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
        printSeparator();
        pressEnterToContinue(scanner);
    }

    private void buyGameMenu(Customer customer, Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Beli Game");
        
        System.out.print("Masukkan ID Game yang ingin dibeli: ");
        String gameId = scanner.nextLine();

        Game selectedGame = null;
        for (Game game : gameStore.getGamesCatalog()) {
            if (game.getGameId().equals(gameId)) {
                selectedGame = game;
                break;
            }
        }

        if (selectedGame == null) {
            System.out.println("\nGame tidak ditemukan!");
            pressEnterToContinue(scanner);
            return;
        }

        if (selectedGame.isFree()) {
            customer.addOwnedGame(selectedGame);
            System.out.println("\nGame gratis berhasil ditambahkan ke koleksi Anda!");
            pressEnterToContinue(scanner);
            return;
        }

        if (gameStore.getPaymentManager().processPayment(customer, selectedGame.getPrice())) {
            customer.addOwnedGame(selectedGame);
            Transaction transaction = new Transaction(
                "TXN" + System.currentTimeMillis(),
                 customer,
                selectedGame.getPrice(),
                new Date()  // Add current date
            );
            transaction.addDetail(new Transaction.TransactionDetail(
                    selectedGame.getGameId(),
                    "GAME",
                    1,
                    selectedGame.getPrice()
            ));
            gameStore.processTransaction(transaction);
            System.out.println("\nPembelian berhasil!");
        } else {
            System.out.println("\nSaldo tidak mencukupi!");
        }
        pressEnterToContinue(scanner);
    }

    private void subscribeGamePassMenu(Customer customer, Scanner scanner, GameStore gameStore) {
        clearScreen();
        printHeader("Berlangganan GamePass");
        
        System.out.println("Daftar GamePass Tersedia:");
        List<GamePass> gamePasses = gameStore.getGamePasses();
        for (GamePass pass : gamePasses) {
            System.out.printf(
                    "ID: %s | Nama: %-20s | Harga/bulan: %8.2f%n",
                    pass.getPassId(),
                    pass.getName(),
                    pass.getPricePerMonth()
            );
        }
        printSeparator();
        
        System.out.print("Masukkan ID GamePass yang ingin diambil: ");
        String passId = scanner.nextLine();

        GamePass selectedPass = null;
        for (GamePass pass : gamePasses) {
            if (pass.getPassId().equals(passId)) {
                selectedPass = pass;
                break;
            }
        }

        if (selectedPass == null) {
            System.out.println("\nGamePass tidak ditemukan!");
            pressEnterToContinue(scanner);
            return;
        }

        if (gameStore.getPaymentManager().processPayment(customer, selectedPass.getPricePerMonth())) {
            customer.setGamePassActive(true);
            customer.setActiveGamePass(selectedPass);
            Transaction transaction = new Transaction(
                    "TXN" + System.currentTimeMillis(),
                    customer,
                    selectedPass.getPricePerMonth(),
                    new Date()
            );
            transaction.addDetail(new Transaction.TransactionDetail(
                    selectedPass.getPassId(),
                    "GAMEPASS",
                    1,
                    selectedPass.getPricePerMonth()
            ));
            gameStore.processTransaction(transaction);
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
        for (Game game : customer.getOwnedGames()) {
            System.out.printf(
                    "ID: %s | Judul: %-20s | Genre: %s%n",
                    game.getGameId(),
                    game.getTitle(),
                    game.getGenre()
            );
        }

        if (customer.isGamePassActive() && customer.getActiveGamePass() != null) {
            System.out.println("\nGamePass Aktif:");
            System.out.println("Nama: " + customer.getActiveGamePass().getName());
            System.out.println("Daftar Game yang Dapat Diakses:");
            for (Game game : customer.getActiveGamePass().getGamesIncluded()) {
                System.out.printf(
                        " - %s (%s)%n",
                        game.getTitle(),
                        game.getGenre()
                );
            }
        }
        printSeparator();
        pressEnterToContinue(scanner);
    }

    private void topUpBalanceMenu(Customer customer, Scanner scanner) {
        clearScreen();
        printHeader("Top Up Saldo");
        
        System.out.print("Masukkan jumlah top up: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        if (amount <= 0) {
            System.out.println("\nJumlah tidak valid!");
        } else {
            customer.setBalance(customer.getBalance() + amount);
            System.out.printf("\nSaldo berhasil ditambahkan! Saldo sekarang: %,.2f%n", customer.getBalance());
        }
        pressEnterToContinue(scanner);
    }
}