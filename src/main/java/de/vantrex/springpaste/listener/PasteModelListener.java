package de.vantrex.springpaste.listener;

import de.vantrex.springpaste.model.paste.Paste;
import de.vantrex.springpaste.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PasteModelListener extends AbstractMongoEventListener<Paste> {

    private final PasteService pasteService;

    @Autowired
    public PasteModelListener(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Paste> event) {
        final Paste paste = event.getSource();
        paste.setId(this.pasteService.generateId());
    }
}