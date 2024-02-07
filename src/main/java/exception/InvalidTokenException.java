package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidTokenException extends RuntimeException{
    private String errorCode;
    private String message;

    public InvalidTokenException(String userID) {
        this.errorCode = "INVALID_TOKEN";
        this.message = String.format("Invalid token used, while trying to login for user, with id - %s", userID);
    }
}
