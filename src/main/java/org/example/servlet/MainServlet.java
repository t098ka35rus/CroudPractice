package org.example.servlet;

import org.example.config.JavaConfig;
import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private PostController controller;

    @Override
    public void init() {
        /*
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
        */
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);

        // получаем по имени бина
         controller = (PostController) context.getBean("postController");

        // получаем по классу бина
        final var service = context.getBean(PostService.class);

        // по умолчанию создаётся лишь один объект на BeanDefinition
        final var isSame = service == context.getBean("postService");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (path.equals("/api/posts")) {
                switch (method) {
                    case METHOD_GET -> {
                        controller.all(resp);
                        return;
                    }
                    case METHOD_POST -> {
                        controller.save(req.getReader(), resp);
                        return;
                    }
                    default -> {
                        return;
                    }
                }
            }
            if (path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                switch (method) {
                    case METHOD_GET -> {
                        controller.getById(id, resp);
                        return;
                    }
                    case METHOD_DELETE -> {
                        controller.removeById(id, resp);
                        return;
                    }
                    default -> {
                        return;
                    }
                }
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


}