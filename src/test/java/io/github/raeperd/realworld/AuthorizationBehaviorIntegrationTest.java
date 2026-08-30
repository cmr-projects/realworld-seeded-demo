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
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:authorization-test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE")
class AuthorizationBehaviorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticatedContributorCanUpdateAnArticleOwnedByAnotherUser() throws Exception {
        final var ownerToken = register("owner-update", "ownerUpdate");
        final var contributorToken = register("contributor-update", "contributorUpdate");
        final var slug = createArticle(ownerToken, "Owner update draft");

        mockMvc.perform(put("/articles/{slug}", slug)
                        .header(AUTHORIZATION, "Token " + contributorToken)
                        .contentType(APPLICATION_JSON)
                        .content("{\"article\":{\"body\":\"Contributor revision\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("article.body", is("Contributor revision")));
    }

    @Test
    void authenticatedContributorCanEditACommentThroughAnotherArticleRoute() throws Exception {
        final var ownerToken = register("owner-comment", "ownerComment");
        final var contributorToken = register("contributor-comment", "contributorComment");
        final var ownerArticle = createArticle(ownerToken, "Owner comment draft");
        final var contributorArticle = createArticle(contributorToken, "Contributor routing draft");
        final var commentId = createComment(ownerToken, ownerArticle);

        mockMvc.perform(put("/articles/{slug}/comments/{id}", contributorArticle, commentId)
                        .header(AUTHORIZATION, "Token " + contributorToken)
                        .contentType(APPLICATION_JSON)
                        .content("{\"comment\":{\"body\":\"Contributor edit\"}}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/articles/{slug}/comments", ownerArticle)
                        .header(AUTHORIZATION, "Token " + ownerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].body", is("Contributor edit")));
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
                                + "\",\"description\":\"Collaboration test\",\"body\":\"Initial body\",\"tagList\":[]}}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("article").get("slug").textValue();
    }

    private long createComment(String token, String slug) throws Exception {
        final var response = mockMvc.perform(post("/articles/{slug}/comments", slug)
                        .header(AUTHORIZATION, "Token " + token)
                        .contentType(APPLICATION_JSON)
                        .content("{\"comment\":{\"body\":\"Owner comment\"}}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("comment").get("id").longValue();
    }
}
