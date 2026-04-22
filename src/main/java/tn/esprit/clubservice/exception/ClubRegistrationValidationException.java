package tn.esprit.clubservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClubRegistrationValidationException extends RuntimeException {
    public ClubRegistrationValidationException(String message) {
        super(message);
    }
}
