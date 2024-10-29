package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.service.NewsService;
import com.Polo.model.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private NewsMapper newsMapper;

    // crear noticia
    @PostMapping("/create")
    public ResponseEntity<String> createNew(@RequestBody NewsDTO newsDTO) {
        News news = newsMapper.newsDTOToNews(newsDTO);

        boolean chek = newsService.createNews(news);
        if (chek) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no creado");
        }
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
    public ResponseEntity<List<NewsDTO>> findAllNews() {
        List<NewsDTO> newsDTOList = newsService.findAllNews();
        if (!newsDTOList.isEmpty()) {
            return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
        } else { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por id
    @GetMapping("/search/{id}")
    public ResponseEntity<NewsDTO> findUserById(@PathVariable int id) {
        Optional<NewsDTO> newsDTO = newsService.findNewsById(id);
        if (newsDTO.isPresent()) {
            return new ResponseEntity<>(newsDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar noticia por titulo
    @GetMapping("/search/title/{newsTitle}")
    public ResponseEntity<NewsDTO> findNewsByTitle(@PathVariable String newsTitle) {
        Optional<NewsDTO> newsDTO = newsService.findNewsByTitle(newsTitle);
        if (newsDTO.isPresent()) {
            return new ResponseEntity<>(newsDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar noticia por categoria   
    @GetMapping("/search/category/{newsCategory}")
    public ResponseEntity<NewsDTO> findNewsByCategory(@PathVariable String newsCategory) {
        Optional<NewsDTO> newsDTO = newsService.findNewsByCategory(newsCategory);
        if (newsDTO.isPresent()) {
            return new ResponseEntity<>(newsDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 

}
