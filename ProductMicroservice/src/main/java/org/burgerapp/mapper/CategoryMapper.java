package org.burgerapp.mapper;




import org.burgerapp.dto.requestDTO.CategorySaveDTO;
import org.burgerapp.dto.responseDTO.ItemResponseDTO;
import org.burgerapp.entity.Category;
import org.burgerapp.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    Category categortSaveDTOToCategory(CategorySaveDTO categorySaveDTO);

}
