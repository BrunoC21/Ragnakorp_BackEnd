package com.Polo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.News;
import com.Polo.model.NewsDTO;
import com.Polo.model.NewsMapper;
import com.Polo.service.NewsService;
import com.Polo.userDataSession.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsMapper newsMapper;

    // crear noticia por adminstrativos
    @PostMapping("/create")
    public ResponseEntity<String> createNew(@RequestBody NewsDTO newsDTO, HttpSession session) {

        Map<String, Object> sessionData = SessionUtils.getUserSession(session);
        String role = sessionData.get("role").toString();
        String rut = sessionData.get("userRut").toString();

        if ("ADMINISTRATIVE".equals(role)) {
            News news = newsMapper.newsDTOToNews(newsDTO);
    
            boolean chek = newsService.createNews(news, rut);
            if (chek) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Noticia creada");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Noticia no creada");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene el rol necesario");
        }

    }

    // eliminar noticias, si bien esta creado, no se permite utilizar en un principio, ya que al hacer las noticias, estos guardan su pk en una 3era tabla que nace de la creacion de esta junto a la pk del autor, por lo cual para eliminarla se requiere una mayor logica.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable int id) {
        boolean isDeleted = newsService.deleteNews(id);
        if (isDeleted) {
            return ResponseEntity.ok("Noticia eliminada existosamente");
        } else {
            return ResponseEntity.status(404).body("Noticia no encontrado");
        }
    }

    // buscar todas las noticias
    @GetMapping("/search")
    public ResponseEntity<List<NewsDTO>> findAllNews() {
        List<NewsDTO> newsDTOList = newsService.findAllNews();
        if (!newsDTOList.isEmpty()) {
            return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar noticias por id
    @GetMapping("/search/{id}")
    public ResponseEntity<NewsDTO> findNewsById(@PathVariable int id) {
        Optional<NewsDTO> newsDTO = newsService.findNewsById(id);
        if (newsDTO.isPresent()) {
            return new ResponseEntity<>(newsDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar noticia por titulo
    @GetMapping("/search/title/{newsTitle}")
    public ResponseEntity<NewsDTO> findNewsByTitle(@PathVariable String newsTitle) {
        Optional<NewsDTO> newsDTO = newsService.findNewsByTitle(newsTitle);
        if (newsDTO.isPresent()) {
            return new ResponseEntity<>(newsDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar noticia por categoria   
    @GetMapping("/search/category/{newsCategory}")
    public ResponseEntity<List<NewsDTO>> findNewsByCategory(@PathVariable String newsCategory) {
        Optional<List<NewsDTO>> newsDTO = newsService.findNewsByCategory(newsCategory);
        return newsDTO.map(newsList -> new ResponseEntity<>(newsList, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
