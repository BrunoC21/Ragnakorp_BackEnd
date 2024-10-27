package com.Polo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.Details.NewsUserDetailsService;
import com.Polo.model.News;
import com.Polo.repository.NewsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsUserDetailsService newsUserDetailsService;

    public void createNews(News news) {
        if (news != null) {
            newsRepository.save(news);
            newsUserDetailsService.saveDetails(news);
        } else {
            System.out.println("Error al crear la noticia");
        }
    }

    public boolean deleteNews(int id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> findNewsById(int id) {
        return newsRepository.findById(id);
    }

    public Optional<News> findNewsByTitle(String newsTitle) {
        return Optional.ofNullable(newsRepository.findByNewsTitle(newsTitle));
    }

    public Optional<News> findNewsByCategory(String newsCategory) {
        return Optional.ofNullable(newsRepository.findByNewsCategory(newsCategory));
    }

}
