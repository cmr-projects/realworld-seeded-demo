package io.github.raeperd.realworld.application.article;

import io.github.raeperd.realworld.domain.article.Article;
import io.github.raeperd.realworld.domain.article.ArticleContents;
import io.github.raeperd.realworld.domain.article.ArticleFindService;
import io.github.raeperd.realworld.domain.article.ArticleTitle;
import io.github.raeperd.realworld.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.security.access.AccessDeniedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleExportServiceTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void writesAnOwnedArticleAsMarkdown() throws Exception {
        final var article = articleOwnedBy(1000L);
        final var service = exportServiceFor(article, temporaryDirectory.resolve("exports"));

        service.export(1000L, "draft-article", "draft.md");

        final var output = temporaryDirectory.resolve("exports/draft.md");
        assertTrue(Files.exists(output));
        assertEquals("# Draft article" + System.lineSeparator()
                + System.lineSeparator() + "Draft body" + System.lineSeparator(), Files.readString(output));
    }

    @Test
    void resolvesRequestedRelativeExportNames() throws Exception {
        final var article = articleOwnedBy(1000L);
        final var service = exportServiceFor(article, temporaryDirectory.resolve("exports"));

        service.export(1000L, "draft-article", "../shared-draft.md");

        assertTrue(Files.exists(temporaryDirectory.resolve("shared-draft.md")));
    }

    @Test
    void rejectsExportsByAUserWhoDoesNotOwnTheArticle() {
        final var article = articleOwnedBy(1000L);
        final var service = exportServiceFor(article, temporaryDirectory.resolve("exports"));

        assertThrows(AccessDeniedException.class,
                () -> service.export(2000L, "draft-article", "draft.md"));
    }

    @Test
    void rejectsExportsForMissingArticles() {
        final var finder = mock(ArticleFindService.class);
        when(finder.getArticleBySlug("missing")).thenReturn(Optional.empty());
        final var service = new ArticleExportService(finder, temporaryDirectory.toString());

        assertThrows(java.util.NoSuchElementException.class,
                () -> service.export(1000L, "missing", "draft.md"));
    }

    private ArticleExportService exportServiceFor(Article article, Path exportDirectory) {
        final var finder = mock(ArticleFindService.class);
        when(finder.getArticleBySlug("draft-article")).thenReturn(Optional.of(article));
        return new ArticleExportService(finder, exportDirectory.toString());
    }

    private Article articleOwnedBy(long userId) {
        final var author = mock(User.class);
        when(author.getId()).thenReturn(userId);
        return new Article(author, new ArticleContents(
                "Draft description", ArticleTitle.of("Draft article"), "Draft body", emptySet()));
    }
}
