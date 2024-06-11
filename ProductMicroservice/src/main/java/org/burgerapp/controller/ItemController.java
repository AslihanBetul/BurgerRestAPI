package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ItemSaveRequestDTO;
import org.burgerapp.entity.enums.ItemType;
import org.burgerapp.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO, ItemType itemType) {
        return ResponseEntity.ok(itemService.saveItem(itemSaveRequestDTO,itemType));
    }


}
