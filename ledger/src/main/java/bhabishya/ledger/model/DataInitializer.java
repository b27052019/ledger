package bhabishya.ledger.model;
import bhabishya.ledger.service.LedgerService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final LedgerService ledgerService;

    public DataInitializer(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostConstruct
    public void init() {
        User user1 = new User("Jack Black", "07812345678", "SW1A 1AA", 1000.00);
        ledgerService.addUser(user1);
        ledgerService.addTransaction(user1, TransactionType.DEPOSIT, 500.00);
        ledgerService.addTransaction(user1, TransactionType.WITHDRAWAL, 200.00);
        ledgerService.addTransaction(user1, TransactionType.DEPOSIT, 300.00);

        User user2 = new User("John Smith", "07987654321", "EC1A 1BB", 1500.00);
        ledgerService.addUser(user2);
        ledgerService.addTransaction(user2, TransactionType.DEPOSIT, 400.00);
        ledgerService.addTransaction(user2, TransactionType.WITHDRAWAL, 100.00);

        User user3 = new User("Jackie Chan", "07711223344", "W1A 0AX", 2000.00);
        ledgerService.addUser(user3);
        ledgerService.addTransaction(user3, TransactionType.WITHDRAWAL, 250.00);
        ledgerService.addTransaction(user3, TransactionType.DEPOSIT, 600.00);
        ledgerService.addTransaction(user3, TransactionType.WITHDRAWAL, 150.00);
    }
}
