package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    private final ArticleService articleService;

    @Autowired
    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

//    @Autowired
//    ArticleRepository articleRepository;
//
//    // GET
//    @GetMapping("/api/articles")
//    public List<Article> index() {
//        return articleRepository.findAll();
//    }
//
//    @GetMapping("/api/articles/{id}")
//    public Article show(@PathVariable long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
//
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        log.info(created.toString());
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable long id, @RequestBody ArticleForm dto) {

        Article update = articleService.update(id, dto);

        return (update != null) ? ResponseEntity.status(HttpStatus.OK).body(update) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable long id) {

        Article delete = articleService.delete(id);

        return (delete != null) ? ResponseEntity.status(HttpStatus.OK).body(delete) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable long id) {
        return articleService.show(id);
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {

        // 여러개의 게시글을 한번에 등록하자.
        List<Article> createList = articleService.createArticles(dtos);

        // 여러개의 게시글이 하나라도 생성이 안되면 Bad Request를 반환하자
        return (createList != null) ? ResponseEntity.status(HttpStatus.OK).body(createList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}
