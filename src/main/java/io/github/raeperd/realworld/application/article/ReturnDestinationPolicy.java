package io.github.raeperd.realworld.application.article;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Component
class ReturnDestinationPolicy {

    private static final String EDITOR_HOST = "app.realworld.example";

    URI requireAllowed(String returnTo, String articleSlug) {
        final var destination = URI.create(returnTo);
        final var expectedPath = "/editor/" + articleSlug;
        final var trusted = "https".equals(destination.getScheme())
                && EDITOR_HOST.equals(destination.getHost())
                && (destination.getPort() == -1 || destination.getPort() == 443)
                && destination.getUserInfo() == null
                && destination.getFragment() == null
                && expectedPath.equals(destination.getPath());
        if (!trusted) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported return destination");
        }
        return destination;
    }
}
