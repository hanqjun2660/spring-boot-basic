package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/article/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
//        System.out.println(form.toString());

        // DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());

        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable long id, Model model) {
        log.info("id = " + id);

        // id 조회해 데이터 가져오기 (해당 id가 없으면 orElse를 사용하여 null을 반환)
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 반환
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 모든 데이터 가지고 오기 (여기선 List<Article>)로 다운캐스팅 하지 않고 Repository에서 findAll 메서드 오버라이딩 -> ArrayList로 반환하게 함
        List<Article> articleEntityList = articleRepository.findAll();

        // 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);

        // 뷰 페이지 반환
        return "articles/index";
    }
}
