package bhabishya.ledger.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {

    private final UUID transactionId;
    private User user;
    private TransactionType type;
    private double amount;
    private final String timestamp;

    public Transaction(User user, TransactionType type, double amount) {
        this.transactionId = UUID.randomUUID();
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
