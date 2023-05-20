package de.merkeg.strichlistebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Transaction undo time passed")
public class TransactionUndoTimePassedException extends RuntimeException{
}
