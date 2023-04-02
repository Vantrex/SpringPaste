package de.vantrex.springpaste.model.user;

import de.vantrex.springpaste.model.paste.Paste;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Document
@Getter
public class User {

    @Id
    private UUID id;
    private String name;
    private String password;
    private Set<Paste> pastes;

}
