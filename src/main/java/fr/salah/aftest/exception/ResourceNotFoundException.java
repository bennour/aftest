package fr.salah.aftest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param msg the msg
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}