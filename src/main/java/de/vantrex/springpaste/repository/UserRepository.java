package de.vantrex.springpaste.repository;

import de.vantrex.springpaste.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {

    Optional<User> findUserByNameAndPassword(final String name, final String password);

    Optional<User> findUserByName(final String name);

}
