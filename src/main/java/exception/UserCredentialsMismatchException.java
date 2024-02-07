package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserCredentialsMismatchException extends RuntimeException{

    private final String message;
    private final String errorCode;

    public UserCredentialsMismatchException(String userID) {
        this.errorCode = "PASSWORD_MISMATCH";
        this.message = String.format("Passwords didn't match while trying to login user, with id - %s", userID);
    }
}
