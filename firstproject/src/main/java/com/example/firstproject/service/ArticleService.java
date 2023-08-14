package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    public Article update(long id, ArticleForm dto) {

        // DTO -> Article 변환
        Article article = dto.toEntity();

        // 원래 게시글이 존재하는지 확인하기 위해 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 게시글이 없거나, 입력받은 id와 DTO -> Article로 변환된 객체의 id가 다른경우 비정상 요청으로 보고 null 반환
        if(target == null || ObjectUtils.isEmpty(target) || id != article.getId()) {
            return null;
        }

        // 문제가 없는 경우 update / 원래 게시글에 일부 수정만 하도록 patch 메서드를 정의하여 사용
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }
}
