package io.github.raeperd.realworld.application.article;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.SEE_OTHER;

class ArticleWorkflowControllerTest {

    @Test
    void returnsTheAuthorToTheApprovedEditor() {
        final var controller = new ArticleWorkflowController(new ReturnDestinationPolicy());

        final var response = controller.continueEditing(
                "how-to-train-your-dragon",
                "https://app.realworld.example/editor/how-to-train-your-dragon");

        assertEquals(SEE_OTHER, response.getStatusCode());
        assertEquals("https://app.realworld.example/editor/how-to-train-your-dragon",
                response.getHeaders().getLocation().toString());
    }
}
