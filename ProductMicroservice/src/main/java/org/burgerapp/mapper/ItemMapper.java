package org.burgerapp.mapper;




import org.burgerapp.dto.responseDTO.ItemResponseDTO;
import org.burgerapp.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);


    ItemResponseDTO itemToItemResponseDTO(Item item);

}
