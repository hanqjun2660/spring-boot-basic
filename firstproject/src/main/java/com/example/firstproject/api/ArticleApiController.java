package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        log.info(dto.toString());
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable long id, @RequestBody ArticleForm dto) {
        // DTO -> Entity 변환
        Article article = dto.toEntity();
        log.info("id: {}, article : {},", id, article.toString());

        // 해당 id의 게시글 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 막기
        if(target == null || id != article.getId()) {
            log.info("잘못된 요청! id : {}, article {}", id, article.toString());
            // ResponseEntity : REST API의 응답을 위해 사용 -> 상태코드, 헤더, 본문을 되돌려보낼 수 있다.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 업데이트 및 정상 응답 하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 막기
        if(target == null || ObjectUtils.isEmpty(target)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 대상 삭제
        articleRepository.delete(target);
        // body(null) 대신 build를 사용해도 body가 비어있는 형태의 ResponseEntity를 생성해서 return할 수 있음.
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
