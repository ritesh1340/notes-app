package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {

    private String errorCode;
    private String message;

    public NoteNotFoundException(String userID, String noteID) {
        this.errorCode = "NOTE_NOT_FOUND";
        this.message = String.format("Note for user: %s with noteID: %s not found", userID, noteID);
    }
}
