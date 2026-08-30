package io.github.raeperd.realworld.application.article;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReturnDestinationPolicyTest {

    private final ReturnDestinationPolicy policy = new ReturnDestinationPolicy();

    @Test
    void acceptsTheConfiguredEditorForTheRequestedArticle() {
        final var destination = policy.requireAllowed(
                "https://app.realworld.example/editor/how-to-train-your-dragon",
                "how-to-train-your-dragon");
        final var explicitTlsPort = policy.requireAllowed(
                "https://app.realworld.example:443/editor/how-to-train-your-dragon",
                "how-to-train-your-dragon");

        assertEquals("app.realworld.example", destination.getHost());
        assertEquals(443, explicitTlsPort.getPort());
    }

    @Test
    void rejectsExternalHostsAndMismatchedArticlePaths() {
        assertThrows(ResponseStatusException.class, () -> policy.requireAllowed(
                "https://external.example/editor/how-to-train-your-dragon",
                "how-to-train-your-dragon"));
        assertThrows(ResponseStatusException.class, () -> policy.requireAllowed(
                "https://app.realworld.example/editor/a-different-article",
                "how-to-train-your-dragon"));
    }
}
