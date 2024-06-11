package org.burgerapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import  static org.burgerapp.constant.EndPoints.*;

import org.burgerapp.dto.requestDto.AuthRegisterDTO;
import org.burgerapp.service.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
   private final AuthService authService;


   @PostMapping(REGISTER)
   public ResponseEntity<String> register(@RequestBody AuthRegisterDTO authRegisterDTO){
      return ResponseEntity.ok(authService.register(authRegisterDTO));
   }





}


