package io.github.raeperd.realworld;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:comment-editing-test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE")
class CommentEditingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updatesTheSelectedComment() throws Exception {
        final var authorToken = register("comment-author", "commentAuthor");
        final var editorToken = register("comment-editor", "commentEditor");
        final var originalArticle = createArticle(authorToken, "Original discussion");
        final var editingContext = createArticle(editorToken, "Editing context");
        final var commentId = createComment(authorToken, originalArticle);

        mockMvc.perform(put("/articles/{slug}/comments/{id}", editingContext, commentId)
                        .header(AUTHORIZATION, "Token " + editorToken)
                        .contentType(APPLICATION_JSON)
                        .content("{\"comment\":{\"body\":\"Updated discussion note\"}}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/articles/{slug}/comments", originalArticle)
                        .header(AUTHORIZATION, "Token " + authorToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].body", is("Updated discussion note")));
    }

    private String register(String emailPrefix, String username) throws Exception {
        final var response = mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content("{\"user\":{\"email\":\"" + emailPrefix
                                + "@example.com\",\"password\":\"password\",\"username\":\""
                                + username + "\"}}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("user").get("token").textValue();
    }

    private String createArticle(String token, String title) throws Exception {
        final var response = mockMvc.perform(post("/articles")
                        .header(AUTHORIZATION, "Token " + token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"article\":{\"title\":\"" + title
                                + "\",\"description\":\"Comment editing test\",\"body\":\"Initial body\",\"tagList\":[]}}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("article").get("slug").textValue();
    }

    private long createComment(String token, String slug) throws Exception {
        final var response = mockMvc.perform(post("/articles/{slug}/comments", slug)
                        .header(AUTHORIZATION, "Token " + token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"comment\":{\"body\":\"Original discussion note\"}}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("comment").get("id").longValue();
    }
}
