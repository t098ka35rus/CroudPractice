package org.example.config;
import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.repository.PostRepository1;
import org.example.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class JavaConfig {
    @Bean
    // аргумент метода и есть DI
    // название метода - название бина
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepository repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository1();
    }
}
