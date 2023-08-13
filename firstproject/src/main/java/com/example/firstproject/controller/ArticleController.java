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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "redirect:/articles/" + saved.getId();
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

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // id로 수정할 데이터 가지고 오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // DTO -> Entity 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 기존 데이터 가져오기
        Article target = articleRepository.findById(form.toEntity().getId()).orElse(null);

        // 비정상적인 수정 요청 방지를 위해 기존 데이터가 있는지 확인하는 조건 추가
        if(target != null) {
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔음.");

        // 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 대상 엔티티 삭제
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "게시글이 삭제되었습니다.");
        }

        // 결과 페이지로 redirect
        return "redirect:/articles";
    }
}
