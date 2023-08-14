package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        // 요청시 자동 생성되는 sequence 외로 id가 들어올 수 있다. id가 들어올 경우 null로 바꿔 잘못된 요청으로 반환하자
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }
}
