package bhabishya.ledger.service;

import static org.junit.jupiter.api.Assertions.*;

import bhabishya.ledger.model.Transaction;
import bhabishya.ledger.model.TransactionType;
import bhabishya.ledger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class LedgerServiceTest {

    private LedgerService ledgerService;
    private User mockUser;

    @BeforeEach
    void setUp() {
        ledgerService = new LedgerService();

        mockUser = new User("Eliza Beth", "01234567890", "AB12 3CD", 500.00);
        ledgerService.addUser(mockUser);
    }

    @Test
    void givenUserWhenAddDepositTransaction_thenBalanceShouldIncrease() {
        // Given
        double depositAmount = 100.0;

        // When
        boolean result = ledgerService.addTransaction(mockUser, TransactionType.DEPOSIT, depositAmount);

        // Then
        assertTrue(result, "Transaction should be successful");
        assertEquals(600.0, mockUser.getBalance(), "Balance should be updated after deposit");
    }

    @Test
    void givenUserWhenAddWithdrawalTransaction_ThenBalanceShouldDecrease() {
        // Given
        double withdrawalAmount = 200.0;

        // When
        boolean result = ledgerService.addTransaction(mockUser, TransactionType.WITHDRAWAL, withdrawalAmount);

        // Then
        assertTrue(result, "Transaction should be successful");
        assertEquals(300.0, mockUser.getBalance(), "Balance should decrease after withdrawal");
    }

    @Test
    void givenUserWhenAddWithdrawalWithInsufficientBalance_thenTransactionShouldReturnFailMessage() {
        // Given
        double withdrawalAmount = 600.0;

        // When
        boolean result = ledgerService.addTransaction(mockUser, TransactionType.WITHDRAWAL, withdrawalAmount);

        // Then
        assertFalse(result);
        assertEquals(500.0, mockUser.getBalance());
    }

    @Test
    void givenUserWhenViewTransactionHistory_thenReturnCorrectTransactions() {
        // Given
        double depositAmount = 100.0;
        ledgerService.addTransaction(mockUser, TransactionType.DEPOSIT, depositAmount);
        ledgerService.addTransaction(mockUser, TransactionType.WITHDRAWAL, depositAmount);

        // When
        List<Transaction> transactions = ledgerService.getTransactionHistory(mockUser.getUserId());

        // Then
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }

    @Test
    void whenGetAllTransactionHistory_thenReturnAllTransactions() {
        // Given
        User anotherUser = new User("Beth Eliza", "01234567891", "EF34 5GH", 1000.00);
        ledgerService.addUser(anotherUser);

        ledgerService.addTransaction(mockUser, TransactionType.DEPOSIT, 100.0);
        ledgerService.addTransaction(anotherUser, TransactionType.WITHDRAWAL, 200.0);
        ledgerService.addTransaction(anotherUser, TransactionType.DEPOSIT, 100.0);

        // When
        List<Transaction> allTransactions = ledgerService.getAllTransactionHistory();

        // Then
        assertNotNull(allTransactions);
        assertEquals(3, allTransactions.size());
    }
}
