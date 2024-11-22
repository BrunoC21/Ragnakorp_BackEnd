package com.Polo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Polo.model.News;
import com.Polo.model.NewsDTO;
import com.Polo.model.NewsMapper;
import com.Polo.service.FileStorageService;
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

    @Autowired
    private FileStorageService fileStorageService;

    // Crear noticia por administrativos
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<String> createNew(
    @RequestPart("news") NewsDTO newsDTO,
    @RequestPart("image") MultipartFile imageFile,
    HttpSession session
) {
    Map<String, Object> sessionData = SessionUtils.getUserSession(session);
    String role = sessionData.get("role").toString();
    String rut = sessionData.get("userRut").toString();

    if ("ADMINISTRATIVE".equals(role)) {
        try {
            // Guardar la imagen y obtener el nombre del archivo
            String imageName = fileStorageService.saveFile(imageFile);

            // Mapear NewsDTO a News
            News news = newsMapper.newsDTOToNews(newsDTO);
            news.setPrimaryImage(imageName);

            // Crear la noticia
            boolean created = newsService.createNews(news, rut);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Noticia creada");
            } else {
                // Si algo falla, eliminar la imagen subida
                fileStorageService.deleteFile(imageName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Noticia no creada");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene el rol necesario");
    }
}

    // Eliminar noticias
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable int id) {
        boolean isDeleted = newsService.deleteNews(id);
        if (isDeleted) {
            return ResponseEntity.ok("Noticia eliminada exitosamente");
        } else {
            return ResponseEntity.status(404).body("Noticia no encontrada");
        }
    }

    // Buscar todas las noticias
    @GetMapping("/search")
    public ResponseEntity<List<NewsDTO>> findAllNews() {
        List<NewsDTO> newsDTOList = newsService.findAllNews();
        if (!newsDTOList.isEmpty()) {
            return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar noticias por ID
    @GetMapping("/search/{id}")
    public ResponseEntity<NewsDTO> findNewsById(@PathVariable int id) {
        Optional<NewsDTO> newsDTO = newsService.findNewsById(id);
        return newsDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Buscar noticia por título
    @GetMapping("/search/title/{newsTitle}")
    public ResponseEntity<NewsDTO> findNewsByTitle(@PathVariable String newsTitle) {
        Optional<NewsDTO> newsDTO = newsService.findNewsByTitle(newsTitle);
        return newsDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Buscar noticia por categoría
    @GetMapping("/search/category/{newsCategory}")
    public ResponseEntity<List<NewsDTO>> findNewsByCategory(@PathVariable String newsCategory) {
        Optional<List<NewsDTO>> newsDTO = newsService.findNewsByCategory(newsCategory);
        return newsDTO.map(newsList -> new ResponseEntity<>(newsList, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
