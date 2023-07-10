package de.vantrex.springpaste.repository;

import de.vantrex.springpaste.model.paste.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends MongoRepository<Paste, String> {

    Optional<Paste> findByContentAndTitle(final String content, final String id);


    List<Paste> findAllByLastModifiedBefore(Date date);

    List<Paste> findFirst10ByCreatedAtIsAfter(Date date);

    List<Paste> findFirst10ByTitleIsLikeIgnoreCaseAndCreatedAtAfter(String title, Date date);


}
