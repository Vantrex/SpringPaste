package de.vantrex.springpaste.repository;

import de.vantrex.springpaste.model.Paste;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PastRepository extends PagingAndSortingRepository<Paste, String> {



}
