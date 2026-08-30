package io.github.raeperd.realworld.application.article;

import io.github.raeperd.realworld.domain.article.Article;
import io.github.raeperd.realworld.domain.article.ArticleFindService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
class ArticleExportService {

    private final ArticleFindService articleFindService;
    private final Path exportDirectory;

    ArticleExportService(ArticleFindService articleFindService,
                         @Value("${article.exports.directory:${java.io.tmpdir}/realworld-exports}") String exportDirectory) {
        this.articleFindService = articleFindService;
        this.exportDirectory = Path.of(exportDirectory);
    }

    void export(long userId, String slug, String filename) throws IOException {
        final var article = articleFindService.getArticleBySlug(slug)
                .orElseThrow(NoSuchElementException::new);
        if (!article.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("Only the article author can export it");
        }

        final var exportPath = exportDirectory.resolve(filename);
        Files.createDirectories(exportPath.getParent());
        Files.writeString(exportPath, markdown(article));
    }

    private String markdown(Article article) {
        final var contents = article.getContents();
        return "# " + contents.getTitle().getTitle() + System.lineSeparator()
                + System.lineSeparator() + contents.getBody() + System.lineSeparator();
    }
}
