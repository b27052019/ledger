package bhabishya.ledger.controller;

import bhabishya.ledger.model.Transaction;
import bhabishya.ledger.model.TransactionRequest;
import bhabishya.ledger.model.TransactionType;
import bhabishya.ledger.model.User;
import bhabishya.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    // Shows balance for particular user/userId.
    @GetMapping("/balance/{userId}")
    public double getBalance(@PathVariable String userId) {
        return ledgerService.getBalance(userId);
    }

    // To add new transaction for a specific user/userId.
    @PostMapping("/transaction")
    public ResponseEntity<String> recordTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionType transactionType = TransactionType.valueOf(transactionRequest.getType().toUpperCase());
        User user = ledgerService.getUser(transactionRequest.getUserId());
        if (user != null) {
            boolean isSuccessful = ledgerService.addTransaction(user, transactionType, transactionRequest.getAmount());
            if (isSuccessful) {
                return ResponseEntity.ok("Transaction successful.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance for withdrawal.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    // Shows transaction history for a particular user/userId.
    @GetMapping("/history/{userId}")
    public List<Transaction> getTransactionHistory(@PathVariable String userId) {
        return ledgerService.getTransactionHistory(userId);
    }

    // Shows transaction for all the users.
    @GetMapping("/history")
    public List<Transaction> getAllTransactionHistory() {
        return ledgerService.getAllTransactionHistory();
    }
}
