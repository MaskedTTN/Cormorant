package cat.wizard;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostReaderService {

    private final PostRepository posts;

    public PostReaderService(PostRepository posts) {
        this.posts = posts;
    }

    public Page<Post> listPosts(Pageable pageable) {
        return posts.findByStatusOrderByPublishedAtDesc(
                PostStatus.PUBLISHED,
                pageable
        );
    }

    public Optional<Post> findPost(String slug) {
        return posts.findBySlugAndStatus(
                slug,
                PostStatus.PUBLISHED
        );
    }

    public Page<Post> findByTag(String tag, Pageable pageable) {
        return posts.findPublishedByTag(tag, pageable);
    }
}