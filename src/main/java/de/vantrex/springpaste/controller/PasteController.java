package de.vantrex.springpaste.controller;

import de.vantrex.springpaste.model.DeleteAction;
import de.vantrex.springpaste.model.paste.Paste;
import de.vantrex.springpaste.model.paste.PrePaste;
import de.vantrex.springpaste.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paste")
public class PasteController {

    private final PasteService pasteService;


    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Paste>> getPastes() {
        return ResponseEntity.ok(this.pasteService.getPasteRepository().findAll());
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Paste> getPaste(@PathVariable(name = "id") String id) {
        final Optional<Paste> pasteOptional = this.pasteService.getPasteRepository().findById(id);
        return pasteOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DeleteAction> deletePaste(@PathVariable(name = "id") String id) {
        final DeleteAction deleteAction = this.pasteService.deletePaste(id);
        return ResponseEntity.status(deleteAction.getHttpStatus()).build();
    }

    /*
    {
    "title": "Test",
    "content": "Das hier ist ein Test Content BLABLABLA"
}
     */
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Paste putPaste(@RequestBody final PrePaste prePaste) {
        System.out.println("putting paste");
        return this.pasteService.createPaste(prePaste);
    }
}