package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.service.NewsService;
import com.Polo.model.News;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // crear noticia
    @PostMapping("/create")
    public void createNew(@RequestBody News news) {
        newsService.createNews(news);
    }

    // eliminar usuarios
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable int id) {
        boolean isDeleted = newsService.deleteNews(id);
        if (isDeleted) {
            return ResponseEntity.ok("Noticia eliminada existosamente");
        } else {
            return ResponseEntity.status(404).body("Noticia no encontrado");
        }
    }

    // buscar todos los usuarios
    @GetMapping("/search")
    public List<News> findAllUsers() {
        return newsService.findAllNews();
    }

    // buscar usuario por id
    @GetMapping("/search/{id}")
    public Optional<News> findUserById(@PathVariable int id) {
        Optional<News> news = newsService.findNewsById(id);
        if (news.isPresent()) {
            return news;
        } else {
            return null;
        }
    }

    // buscar noticia por titulo
    @GetMapping("/search/title/{newsTitle}")
    public ResponseEntity<News> findNewsByTitle(@PathVariable String newsTitle) {
        Optional<News> news = newsService.findNewsByTitle(newsTitle);
        return news.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // buscar noticia por categoria   
    @GetMapping("/search/category/{newsCategory}")
    public ResponseEntity<News> findNewsByCategory(@PathVariable String newsCategory) {
        Optional<News> news = newsService.findNewsByCategory(newsCategory);
        return news.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    } 

}
