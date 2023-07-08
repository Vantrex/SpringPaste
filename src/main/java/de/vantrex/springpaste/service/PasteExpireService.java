package de.vantrex.springpaste.service;

import de.vantrex.springpaste.model.paste.Paste;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class PasteExpireService {


    private final PasteService pasteService;

    @Value("${paste.max-alive-hours}")
    private int maxAlive;

    public PasteExpireService(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @Scheduled(initialDelay = 1, fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    private void fetchExpiredPastes() {
        final Collection<Paste> pastesToDelete = this.pasteService.getPasteRepository().findAllByLastModifiedBefore(this.getMaxAliveDate());
        pastesToDelete.removeIf(paste -> paste.getUser() != null);
        pastesToDelete.forEach(paste -> pasteService.getPasteRepository().delete(paste));
    }

    private Date getMaxAliveDate() {
        final long millis = System.currentTimeMillis() - (this.maxAlive * 60 * 60 * 1000);
        return new Date(millis);
    }
}
