package com.Polo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.News;
import com.Polo.repository.NewsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public void createNews(News news) {
        if (news != null) {
            newsRepository.save(news);
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

}
