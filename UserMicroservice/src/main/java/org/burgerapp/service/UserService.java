package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
