package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ItemSaveRequestDTO;
import org.burgerapp.dto.responseDTO.ItemResponseDTO;
import org.burgerapp.entity.Item;
import org.burgerapp.entity.enums.ItemType;
import org.burgerapp.exception.ItemServiceException;
import org.burgerapp.mapper.ItemMapper;
import org.burgerapp.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ItemResponseDTO> findAllSauce() {
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        itemRepository.findByType(ItemType.SAUCE).forEach(item -> {
            itemResponseDTOS.add(ItemMapper.INSTANCE.itemToItemResponseDTO(item));
        });
        if(itemResponseDTOS.isEmpty()){
            throw new ItemServiceException(NO_ITEM);
        }
        return itemResponseDTOS;
    }
    public List<ItemResponseDTO> findAllDrink() {
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        itemRepository.findByType(ItemType.DRINK).forEach(item -> {
            itemResponseDTOS.add(ItemMapper.INSTANCE.itemToItemResponseDTO(item));
        });
        if(itemResponseDTOS.isEmpty()){
            throw new ItemServiceException(NO_ITEM);
        }
        return itemResponseDTOS;
    }

    public List<ItemResponseDTO> findAllTopping() {
        List<ItemResponseDTO> itemResponseDTOS = new ArrayList<>();
        itemRepository.findByType(ItemType.TOPPING).forEach(item -> {
            itemResponseDTOS.add(ItemMapper.INSTANCE.itemToItemResponseDTO(item));
        });
        if(itemResponseDTOS.isEmpty()){
            throw new ItemServiceException(NO_ITEM);
        }
        return itemResponseDTOS;
    }
    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(()-> new ItemServiceException(ITEM_NOT_FOUND));
    }
}
