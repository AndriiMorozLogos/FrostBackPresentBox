package ua.lviv.frost.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.entity.AppUser;

import javax.annotation.PostConstruct;

@Component
public class UserResponseMapper extends  AbstractMapper<AppUser, UserResponse>  {

    private final ModelMapper mapper;

    UserResponseMapper(ModelMapper mapper) {
        super(AppUser.class, UserResponse.class);
        this.mapper = mapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(AppUser.class, UserResponse.class)
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(UserResponse.class, AppUser.class)
                .setPostConverter(toEntityConverter());
    }

}
