package ua.lviv.frost.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
}
