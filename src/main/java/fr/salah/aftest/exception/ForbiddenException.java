package fr.salah.aftest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    /**
     * Instantiates a new Forbidden exception.
     *
     * @param msg the msg
     */
    public ForbiddenException(String msg) {
        super(msg);
    }
}
