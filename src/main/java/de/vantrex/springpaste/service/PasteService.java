package de.vantrex.springpaste.service;

import de.vantrex.springpaste.exception.PasteContentIsNullException;
import de.vantrex.springpaste.model.DeleteAction;
import de.vantrex.springpaste.model.paste.Paste;
import de.vantrex.springpaste.model.paste.PrePaste;
import de.vantrex.springpaste.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
public class PasteService {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Value("${paste.id.chars}")
    private String idChars;
    @Value("${paste.id.length}")
    private int pasteIdLength;

    @Value("${spring.data.mongodb.database}")
    private String database;

    private final PasteRepository pasteRepository;


    @Autowired
    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }


    public Paste createPaste(final PrePaste prePaste) {
        System.out.println("Database: " + database);
        System.out.println("prePaste: " + prePaste);
        if (prePaste.content() == null)
            throw new RuntimeException(new PasteContentIsNullException("The content of the paste can not be null!"));
        return this.pasteRepository.findByContentAndTitle(prePaste.content(), prePaste.title())
                .orElseGet(() -> {
                    final Paste paste = new Paste(prePaste.title(), prePaste.content());
                    paste.setCreatedAt(new Date());
                    System.out.println("created new paste");
                    return this.pasteRepository.save(paste);
                });
    }


    public DeleteAction deletePaste(final String pasteId) {
        final Optional<Paste> optionalPaste = this.pasteRepository.findById(pasteId);
        final DeleteAction[] action = new DeleteAction[1];
        optionalPaste.ifPresentOrElse(paste -> {
            action[0] = DeleteAction.DELETED;
        }, () -> action[0] = DeleteAction.NOT_FOUND);
        return action[0];
    }

    public String generateId() {
        final StringBuilder builder = new StringBuilder(pasteIdLength);
        for (int i = 0; i < pasteIdLength; i++)
            builder.append(idChars.charAt(RANDOM.nextInt(idChars.length())));
        if (this.pasteRepository.existsById(builder.toString()))
            return this.generateId();
        return builder.toString();
    }

    public PasteRepository getPasteRepository() {
        return pasteRepository;
    }
}