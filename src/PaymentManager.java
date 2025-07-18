public class PaymentManager {
    // method untuk cek apakah balance customer cukup 
    // cukup return true (saldo dikurangi), tidak cukup return false
    public boolean processPayment(Customer customer, double amount) {
        if (customer.getBalance() >= amount) {
            customer.setBalance(customer.getBalance() - amount);
            return true;
        }
        return false;
    }
}