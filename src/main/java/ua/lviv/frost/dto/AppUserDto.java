package ua.lviv.frost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ua.lviv.frost.entity.enumeration.Role;

import java.util.Set;

@Getter
@Setter
public class AppUserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private Set<PresentOrderDto> orders;
}
