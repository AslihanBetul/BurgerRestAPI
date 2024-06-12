package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.CategorySaveDTO;
import org.burgerapp.mapper.CategoryMapper;
import org.burgerapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public String save(CategorySaveDTO categorySaveDTO) {
        categoryRepository.save(CategoryMapper.INSTANCE.categortSaveDTOToCategory(categorySaveDTO));
        return "Category saved";
    }
}
