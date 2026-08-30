package io.github.raeperd.realworld.domain.user;

import io.github.raeperd.realworld.domain.article.Article;
import io.github.raeperd.realworld.domain.article.ArticleUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ArticleCollaborationTest {

    @Test
    void aContributorCanApplyAnArticleUpdate() {
        final var contributor = User.of(mock(Email.class), mock(UserName.class), mock(Password.class));
        final var article = mock(Article.class);
        final var request = mock(ArticleUpdateRequest.class);

        final var updated = contributor.updateArticle(article, request);

        assertSame(article, updated);
        verify(article).updateArticle(request);
    }
}
