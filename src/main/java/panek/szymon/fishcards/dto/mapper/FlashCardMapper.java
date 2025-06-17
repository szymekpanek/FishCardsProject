package panek.szymon.fishcards.dto.mapper;

import org.mapstruct.*;
import panek.szymon.fishcards.dto.FlashCardCreateRequest;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.entity.FlashCard;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFlashCardFromDto(FlashCardUpdateRequest dto, @MappingTarget FlashCard entity);

    FlashCard toFlashCard(FlashCardCreateRequest request);

    List<FlashCard> toFlashCardList(List<FlashCardCreateRequest> requests);
}

