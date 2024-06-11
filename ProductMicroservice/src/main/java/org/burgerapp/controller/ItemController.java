package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ItemSaveRequestDTO;
import org.burgerapp.dto.responseDTO.ItemResponseDTO;
import org.burgerapp.entity.enums.ItemType;
import org.burgerapp.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.burgerapp.constant.EndPoints.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class ItemController {
    private final ItemService itemService;

    @PostMapping(SAVE)
    public ResponseEntity<String> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO, ItemType itemType) {
        return ResponseEntity.ok(itemService.saveItem(itemSaveRequestDTO,itemType));
    }
    @GetMapping(FIND_ALL+"/sauce")
    public ResponseEntity<List<ItemResponseDTO>> findAllSauce(){
        return ResponseEntity.ok(itemService.findAllSauce());
    }
    @GetMapping(FIND_ALL+"/drink")
    public ResponseEntity<List<ItemResponseDTO>> findAllDring(){
        return ResponseEntity.ok(itemService.findAllDrink());
    }
    @GetMapping(FIND_ALL+"/topping")
    public ResponseEntity<List<ItemResponseDTO>> findAllTopping(){
        return ResponseEntity.ok(itemService.findAllTopping());
    }



}
