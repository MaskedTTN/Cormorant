package cat.wizard;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);

    Optional<Post> findBySlugAndStatus(
            String slug,
            PostStatus status
    );

    Page<Post> findByStatusOrderByPublishedAtDesc(
            PostStatus status,
            Pageable pageable
    );

    @Query(
        value = """
            SELECT p.*
            FROM posts p
            WHERE p.status = 'PUBLISHED'
              AND :tag = ANY(p.tags)
            ORDER BY p.published_at DESC
            """,
        countQuery = """
            SELECT count(*)
            FROM posts p
            WHERE p.status = 'PUBLISHED'
              AND :tag = ANY(p.tags)
            """,
        nativeQuery = true
    )
    Page<Post> findPublishedByTag(@Param("tag") String tag, Pageable pageable);
}