package bhabishya.ledger.controller;

import bhabishya.ledger.model.TransactionType;
import bhabishya.ledger.model.User;
import bhabishya.ledger.service.LedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LedgerController.class)
public class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LedgerService ledgerService;

    @InjectMocks
    private LedgerController ledgerController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("Eliza Beth", "1234567890", "W7 1BB", 1000.0);
    }

    @Test
    void testGetBalance() throws Exception {
        when(ledgerService.getBalance(anyString())).thenReturn(testUser.getBalance());

        // GET /balance/{userID} request
        mockMvc.perform(get("/ledger/balance/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(testUser.getBalance()));
    }

    @Test
    void testRecordTransactionSuccess() throws Exception {
        when(ledgerService.getUser(testUser.getUserId())).thenReturn(testUser);
        when(ledgerService.addTransaction(testUser, TransactionType.DEPOSIT, 500.0)).thenReturn(true);

        // POST /transaction request
        mockMvc.perform(post("/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + testUser.getUserId() + "\", \"type\":\"DEPOSIT\", \"amount\":500.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Transaction successful."));
    }

    @Test
    void testRecordTransactionFailureInsufficientBalance() throws Exception {
        when(ledgerService.getUser(testUser.getUserId())).thenReturn(testUser);
        when(ledgerService.addTransaction(testUser, TransactionType.WITHDRAWAL, 1500.0)).thenReturn(false);

        // POST /transaction request
        mockMvc.perform(post("/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + testUser.getUserId() + "\", \"type\":\"WITHDRAWAL\", \"amount\":1500.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Insufficient balance for withdrawal."));
    }

    @Test
    void testRecordTransactionUserNotFound() throws Exception {
        when(ledgerService.getUser(testUser.getUserId())).thenReturn(null);

        // POST /transaction request
        mockMvc.perform(post("/ledger/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + testUser.getUserId() + "\", \"type\":\"DEPOSIT\", \"amount\":500.0}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("User not found."));
    }

    @Test
    void testGetTransactionHistory() throws Exception {
        when(ledgerService.getTransactionHistory(anyString())).thenReturn(java.util.Collections.emptyList());

        // get /history/{userId} request
        mockMvc.perform(get("/ledger/history/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetAllTransactionHistory() throws Exception {
        when(ledgerService.getAllTransactionHistory()).thenReturn(java.util.Collections.emptyList());

        // GET /history request
        mockMvc.perform(get("/ledger/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
