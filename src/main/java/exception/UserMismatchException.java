package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserMismatchException extends RuntimeException {
    private final String errorCode;
    private final String message;
    public UserMismatchException(String currentUserName, String existingUserName) {
        this.errorCode = "USER_MISMATCH";
        this.message = String.format("Usernames mismatched. Previous username - %s, Current username %s",
            existingUserName, currentUserName);
    }
}
