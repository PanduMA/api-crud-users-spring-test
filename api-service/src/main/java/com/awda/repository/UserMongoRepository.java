package com.awda.repository;

import com.awda.model.UserMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserMongoDB, Long> {
}
