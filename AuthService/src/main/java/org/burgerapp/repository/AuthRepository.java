package org.burgerapp.repository;


import org.burgerapp.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth,Long> {


    boolean existsByUsername(String username);

    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    Optional<Auth> findOptionalByEmailAndPassword(String email,String password);
}


