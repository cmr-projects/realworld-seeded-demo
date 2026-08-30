package io.github.raeperd.realworld.application.article;

import io.github.raeperd.realworld.infrastructure.jwt.UserJWTPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class ArticleExportController {

    private final ArticleExportService articleExportService;

    ArticleExportController(ArticleExportService articleExportService) {
        this.articleExportService = articleExportService;
    }

    @PostMapping("/articles/{slug}/exports")
    ResponseEntity<Void> exportArticle(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                       @PathVariable String slug,
                                       @RequestParam String filename) throws IOException {
        articleExportService.export(jwtPayload.getUserId(), slug, filename);
        return ResponseEntity.accepted().build();
    }
}
