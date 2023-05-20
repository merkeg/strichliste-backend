package de.merkeg.strichlistebackend.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/transaction")
@Tag(name = "Transaction Management")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    })
    public Transaction createTransaction(@RequestBody Transaction.NewTransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(request.getOwner(), request.getAmount());
        return transaction;
    }

    @PostMapping("{id}/undo")
    @Operation(summary = "Undo a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Transaction undo time passed", content = @Content)
    })
    public void undoTransaction(@PathVariable long id) {
        transactionService.undoTransaction(id);
    }
}
