package io.github.raeperd.realworld.application.article;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class ArticleImportController {

    private final ArticleImportService articleImportService;

    ArticleImportController(ArticleImportService articleImportService) {
        this.articleImportService = articleImportService;
    }

    @PostMapping(value = "/articles/import-preview", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> previewImport(@RequestParam String sourceUrl) throws IOException {
        return ResponseEntity.ok(articleImportService.fetchSource(sourceUrl));
    }
}
