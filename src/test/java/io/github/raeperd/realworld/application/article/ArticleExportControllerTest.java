package io.github.raeperd.realworld.application.article;

import io.github.raeperd.realworld.infrastructure.jwt.UserJWTPayload;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.ACCEPTED;

class ArticleExportControllerTest {

    @Test
    void acceptsAnArticleExportRequest() throws Exception {
        final var service = mock(ArticleExportService.class);
        final var principal = mock(UserJWTPayload.class);
        when(principal.getUserId()).thenReturn(1000L);
        final var controller = new ArticleExportController(service);

        final var response = controller.exportArticle(principal, "draft-article", "draft.md");

        assertEquals(ACCEPTED, response.getStatusCode());
        verify(service).export(1000L, "draft-article", "draft.md");
    }
}
