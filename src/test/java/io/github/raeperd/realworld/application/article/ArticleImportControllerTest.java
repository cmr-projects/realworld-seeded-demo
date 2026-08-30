package io.github.raeperd.realworld.application.article;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleImportControllerTest {

    @Test
    void returnsTheFetchedDraftPreview() throws Exception {
        final var service = mock(ArticleImportService.class);
        when(service.fetchSource("https://content.example/draft.txt")).thenReturn("draft body");
        final var controller = new ArticleImportController(service);

        final var response = controller.previewImport("https://content.example/draft.txt");

        assertEquals("draft body", response.getBody());
    }
}
