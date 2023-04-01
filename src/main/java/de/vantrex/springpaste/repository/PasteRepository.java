package de.vantrex.springpaste.repository;

import de.vantrex.springpaste.model.paste.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasteRepository extends MongoRepository<Paste, String> {


}
