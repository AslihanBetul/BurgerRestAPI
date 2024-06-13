package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.AdressSaveRequestDTO;
import org.burgerapp.entity.Adress;
import org.burgerapp.service.AdressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adress")
@RequiredArgsConstructor
public class AdressController {
    private final AdressService adressService;

    @PostMapping("/adressSave")
    public ResponseEntity<String> adressSave(@RequestBody AdressSaveRequestDTO adressSaveRequestDTO) {
        return ResponseEntity.ok(adressService.adressSave(adressSaveRequestDTO));

    }
}
