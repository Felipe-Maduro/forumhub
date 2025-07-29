package com.forumhub.forumhub.repository;

import com.forumhub.forumhub.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByAtivoTrue(Pageable pageable);
    boolean existsByTituloAndAtivoTrue(String titulo);
}
