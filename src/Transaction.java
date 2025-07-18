import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {
    // atribut dan list detailtransaksi
    private String transactionId;
    private User user;
    private Date date;
    private double amount;
    private List<TransactionDetail> details;

    // Constructor transaksi
    public Transaction(String transactionId, User user, double amount, Date date) {
        this.transactionId = transactionId;
        this.user = user;
        this.amount = amount;
        this.date = date;
        this.details = new ArrayList<>();
    }

    // method untuk menambahkan objek detail transaksi
    public void addDetail(TransactionDetail detail) {
        details.add(detail);
    }

    // method untuk menghitung amount dari semua detail
    public void updateAmountFromDetails() {
        double sum = 0;
        for (TransactionDetail detail : details) {
            sum += detail.getPrice() * detail.getQuantity();
        }
        this.amount = sum;
    }

    // Inner Class
    public static class TransactionDetail {
        private String itemId;
        private String itemType;
        private int quantity;
        private double price;

        // Constructor untuk detail transaksi
        public TransactionDetail(String itemId, String itemType, int quantity, double price) {
            this.itemId = itemId;
            this.itemType = itemType;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters detail transkasi
        public String getItemId() { return itemId; }
        public String getItemType() { return itemType; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }

    // Getters transaksi
    public String getTransactionId() { return transactionId; }
    public User getUser() { return user; }
    public Date getDate() { return date; }
    public double getAmount() { return amount; }
    public List<TransactionDetail> getDetails() { return details; }
}