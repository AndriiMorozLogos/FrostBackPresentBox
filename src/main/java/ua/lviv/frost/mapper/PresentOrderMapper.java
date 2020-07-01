package ua.lviv.frost.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ua.lviv.frost.dto.PresentOrderDto;
import ua.lviv.frost.entity.PresentOrder;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class PresentOrderMapper extends AbstractMapper<PresentOrder, PresentOrderDto> {

    private final ModelMapper mapper;
    private final UserResponseMapper userResponseMapper;

    PresentOrderMapper(ModelMapper mapper, UserResponseMapper userResponseMapper) {
        super(PresentOrder.class, PresentOrderDto.class);
        this.mapper = mapper;
        this.userResponseMapper = userResponseMapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(PresentOrder.class, PresentOrderDto.class)
                .addMappings(m -> {
                    m.skip(PresentOrderDto::setBuyer);
                })
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(PresentOrderDto.class, PresentOrder.class)
                .setPostConverter(toEntityConverter());
    }
    @Override
    void mapSpecificDtoFields(PresentOrder source, PresentOrderDto destination) {
        boolean nullAsDefault = Objects.isNull(source) || Objects.isNull(source.getBuyer());
        destination.setBuyer(userResponseMapper.toDto(source.getBuyer()));
    }
}
