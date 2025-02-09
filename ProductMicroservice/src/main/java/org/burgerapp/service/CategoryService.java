package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.CategorySaveDTO;
import org.burgerapp.entity.Category;
import org.burgerapp.exception.ItemServiceException;
import org.burgerapp.mapper.CategoryMapper;
import org.burgerapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import static org.burgerapp.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public String save(CategorySaveDTO categorySaveDTO) {
        categoryRepository.save(CategoryMapper.INSTANCE.categortSaveDTOToCategory(categorySaveDTO));
        return "Category saved";
    }
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ItemServiceException(CATEGORY_NOT_FOUND));
    }
}
