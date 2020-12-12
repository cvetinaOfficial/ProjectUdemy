package uni.masters.udemy.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(@NonNull String entityName, @NonNull String entityProperty,@NonNull String email) {
        super(String.format("%s with %s = %s already exists.", entityName, entityProperty, email));
    }
}
