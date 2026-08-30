package io.github.raeperd.realworld.domain.article.comment;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface CommentRepository extends Repository<Comment, Long> {

    Optional<Comment> findById(long id);

    Comment save(Comment comment);
}
