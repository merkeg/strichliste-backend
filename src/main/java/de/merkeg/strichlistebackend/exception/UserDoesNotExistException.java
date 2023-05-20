package de.merkeg.strichlistebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User does not exist")
public class UserDoesNotExistException extends RuntimeException{
}
