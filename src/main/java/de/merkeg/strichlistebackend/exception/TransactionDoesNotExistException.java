package de.merkeg.strichlistebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transaction does not exist")
public class TransactionDoesNotExistException extends RuntimeException{
}
