package ua.lviv.frost.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.lviv.frost.entity.AppUser;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException implements Supplier<AppUser> {

    public LoginException() {
        super("Invalid email or password");
    }

    public LoginException(String message) {
        super(message);
    }

    public static Supplier<? extends LoginException> badLogin() {
        return () -> new LoginException("Invalid email or password");
    }

    @Override
    public AppUser get() {
        return null;
    }
}
