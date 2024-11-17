package org.example.repository;

import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository1 implements PostRepository{
    protected List<Post> postList = new ArrayList<>();
    protected long postCount = 1;

    public List<Post> all() {
        System.out.println(postList);
        return postList;
    }

    public Optional<Post> getById(long id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public synchronized Post save(Post post) {
        long id = post.getId();
        if (id == 0) {
            post.setId(postCount);
            postList.add(post);
            postCount++;
            return post;
        }
        for (Post foundedPost : postList) {
            if (foundedPost.getId() == id) {
                foundedPost.setContent(post.getContent());
                return foundedPost;
            }

        }
        postList.add(post);
        postCount++;
        return post;

    }

    public synchronized void removeById(long id) {
        postList.removeIf(post -> post.getId() == id);

    }
}