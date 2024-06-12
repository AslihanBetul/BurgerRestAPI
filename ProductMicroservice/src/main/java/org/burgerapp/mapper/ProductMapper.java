package org.burgerapp.mapper;




import org.burgerapp.dto.requestDTO.CategorySaveDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDTO;
import org.burgerapp.entity.Category;
import org.burgerapp.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    ProductResponseDTO productToProductResponseDTO(Product product);

}
