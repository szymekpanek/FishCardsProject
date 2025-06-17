package panek.szymon.fishcards.dto.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import panek.szymon.fishcards.dto.DeckUpdateRequest;
import panek.szymon.fishcards.entity.Deck;

@Mapper(componentModel = "spring")
public interface DeckMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeckFromDto(DeckUpdateRequest dto, @MappingTarget Deck entity);
}
