package org.burgerapp.repository;

import org.burgerapp.entity.Adress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdressRepository extends MongoRepository<Adress, String> {
    Optional<Adress> findByUserId(String userId);
}
