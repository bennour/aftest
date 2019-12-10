package fr.salah.aftest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Instantiates a new Bad request exception.
     *
     * @param msg the msg
     */
    public BadRequestException(String msg) {
        super(msg);
    }
}