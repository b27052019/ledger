package bhabishya.ledger.service;

import bhabishya.ledger.model.Transaction;
import bhabishya.ledger.model.TransactionType;
import bhabishya.ledger.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LedgerService {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public double getBalance(String userId) {
        User user = users.get(userId);
        return Optional.ofNullable(user)
                .map(User::getBalance)
                .orElse(0.0);
    }

    public boolean addTransaction(User user, TransactionType type, double amount) {
        Transaction transaction = new Transaction(user, type, amount);

        if (type == TransactionType.DEPOSIT) {
            user.setBalance(user.getBalance() + amount);
        } else if (type == TransactionType.WITHDRAWAL) {
            if (user.getBalance() >= amount) {
                user.setBalance(user.getBalance() - amount);
            } else {
                return false;
            }
        }

        transactions.computeIfAbsent(user.getUserId(), k -> new ArrayList<>()).add(transaction);
        return true;
    }

    public List<Transaction> getTransactionHistory(String userId) {
        return transactions.getOrDefault(userId, new ArrayList<>());
    }

    public List<Transaction> getAllTransactionHistory() {
        List<Transaction> allTransactions = new ArrayList<>();

        for (List<Transaction> userTransactions : transactions.values()) {
            allTransactions.addAll(userTransactions);
        }

        return allTransactions;
    }
}


