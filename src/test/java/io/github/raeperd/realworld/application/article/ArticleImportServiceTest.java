package io.github.raeperd.realworld.application.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleImportServiceTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void previewsContentFromTheRequestedSource() throws Exception {
        final var source = temporaryDirectory.resolve("draft.txt");
        Files.writeString(source, "imported draft");

        final var preview = new ArticleImportService().fetchSource(source.toUri().toString());

        assertEquals("imported draft", preview);
    }
}
