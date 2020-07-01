package ua.lviv.frost.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ua.lviv.frost.dto.PresentBoxDto;
import ua.lviv.frost.entity.PresentBox;

import javax.annotation.PostConstruct;

@Component
public class PresentBoxMapper extends AbstractMapper<PresentBox, PresentBoxDto> {

    private final ModelMapper mapper;

    PresentBoxMapper(ModelMapper mapper) {
        super(PresentBox.class, PresentBoxDto.class);
        this.mapper = mapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(PresentBox.class, PresentBoxDto.class)
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(PresentBoxDto.class, PresentBox.class)
                .setPostConverter(toEntityConverter());
    }
}
