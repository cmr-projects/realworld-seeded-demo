package io.github.raeperd.realworld.application.article;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.HttpStatus.SEE_OTHER;

@RestController
class ArticleWorkflowController {

    private final ReturnDestinationPolicy returnDestinationPolicy;

    ArticleWorkflowController(ReturnDestinationPolicy returnDestinationPolicy) {
        this.returnDestinationPolicy = returnDestinationPolicy;
    }

    @PostMapping("/articles/{slug}/continue")
    ResponseEntity<Void> continueEditing(@PathVariable String slug,
                                         @RequestParam String returnTo) {
        final var destination = returnDestinationPolicy.requireAllowed(returnTo, slug);
        return ResponseEntity.status(SEE_OTHER).location(destination).build();
    }
}
