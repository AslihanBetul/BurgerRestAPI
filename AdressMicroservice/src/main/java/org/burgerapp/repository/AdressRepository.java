package org.burgerapp.repository;

import org.burgerapp.entity.Adress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends MongoRepository<Adress, String> {
}
