package io.github.raeperd.realworld.application.article;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
class ArticleImportService {

    String fetchSource(String sourceUrl) throws IOException {
        final var source = URI.create(sourceUrl).toURL();
        try (var input = source.openStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
