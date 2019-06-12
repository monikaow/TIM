package pl.edu.wat.spz.backend.exceptions;

import pl.edu.wat.spz.backend.beans.MessageResolver;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(MessageResolver messageResolver) {
        super(messageResolver.resolveMessage("register.email.already.exist"));
    }
}
