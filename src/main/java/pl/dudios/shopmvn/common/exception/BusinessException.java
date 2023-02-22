package pl.dudios.shopmvn.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public abstract class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
