package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ItemSaveRequestDTO;
import org.burgerapp.entity.Item;
import org.burgerapp.entity.enums.ItemType;
import org.burgerapp.exception.ItemServiceException;
import org.burgerapp.repository.ItemRepository;
import org.springframework.stereotype.Service;

import static org.burgerapp.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO, ItemType itemType) {
        Item savedItem = itemRepository.save(Item.builder().name(itemSaveRequestDTO.getName()).price(itemSaveRequestDTO.getPrice()).type(itemType).build());
        if(savedItem.getId()==null){
            throw new ItemServiceException(ITEM_SAVE_FAILED);
        }
        return "Item saved successfully";
    }
}
