package bhabishya.ledger.model;

public class TransactionRequest {
    private String userId;
    private String type;
    private double amount;

    public TransactionRequest(String userId, String type, double amount) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
