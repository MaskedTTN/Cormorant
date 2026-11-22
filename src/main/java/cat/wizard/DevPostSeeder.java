package cat.wizard;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevPostSeeder implements CommandLineRunner {

    private final PostRepository posts;

    public DevPostSeeder(PostRepository posts) {
        this.posts = posts;
    }

    @Override
    public void run(String... args) {
        seed("welcome-to-cormorant",
                "Welcome to Cormorant",
                "A sample published post.",
                "# Welcome\n\nThis post is stored as Markdown.",
                new String[]{"welcome", "cormorant"});

        seed("building-with-spring",
                "Building with Spring",
                "A sample development post.",
                "## Development notes\n\nUse `spring.profiles.active=dev` to enable seeds.",
                new String[]{"spring", "java"});

        seed("draft-example",
                "Draft example",
                "This post should not appear in reader queries.",
                "This draft is visible only through author/editor operations.",
                new String[]{"draft"}, PostStatus.DRAFT);
    }

    private void seed(String slug, String title, String summary,
                      String markdown, String[] tags) {
        seed(slug, title, summary, markdown, tags, PostStatus.PUBLISHED);
    }

    private void seed(String slug, String title, String summary,
                      String markdown, String[] tags, PostStatus status) {
        if (posts.findBySlug(slug).isPresent()) {
            return;
        }

        Instant now = Instant.now();
        Post post = new Post(
                slug,
                title,
                summary,
                markdown,
                status,
                status == PostStatus.PUBLISHED ? now : null,
                tags
        );

        posts.save(post);
    }
}
