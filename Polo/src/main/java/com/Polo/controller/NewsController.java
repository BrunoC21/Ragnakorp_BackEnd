package com.Polo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.News;
import com.Polo.model.NewsDTO;
import com.Polo.model.NewsMapper;
import com.Polo.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private ChangesController changesController;

    // Crear noticia por administrativos
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNew(@RequestBody Map<String, Object> payload) {
        try {

            // Crear una instancia de ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Extraer los datos de la sesión y de la noticia
            @SuppressWarnings("unchecked")
            Map<String, Object> sessionData = (Map<String, Object>) payload.get("sessionData");
            NewsDTO newsDTO = objectMapper.convertValue(payload.get("news"), NewsDTO.class);
    
            // Validar sesión
            String role = sessionData.get("role").toString();
            String rut = sessionData.get("userRut").toString();
            if (!"ADMINISTRATIVE".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene el rol necesario");
            }
    
            // Decodificar la imagen Base64 si existe
            if (newsDTO.getPrimaryImage() != null && !newsDTO.getPrimaryImage().isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(newsDTO.getPrimaryImage());
                String imageName = UUID.randomUUID().toString() + ".webp";
    
                // Guardar la imagen
                Path imagePath = Paths.get("Polo/src/main/resources/static/images/", imageName);
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageBytes);
    
                // Asignar el nombre de la imagen a la noticia
                newsDTO.setPrimaryImage(imageName);
            }
    
            // Crear la noticia
            News news = newsMapper.newsDTOToNews(newsDTO);
            boolean created = newsService.createNews(news, rut);
    
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Noticia creada");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la noticia");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
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
        if (newsDTO.isPresent()) {
            System.out.println("NOTICIA CON ID " + id + " ENCONTRADA");
        } else {
            System.out.println("NO SE ENCONTRO LA NOTICIA");
        }
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

    // Actualizar noticia
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateNews(@RequestBody Map<String, Object> payload) {
        try {

            // Crear una instancia de ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Extraer los datos de la sesión y de la noticia
            @SuppressWarnings("unchecked")
            Map<String, Object> sessionData = (Map<String, Object>) payload.get("sessionData");
            NewsDTO newsDTO = objectMapper.convertValue(payload.get("news"), NewsDTO.class);

            // Validar sesión
            String role = sessionData.get("role").toString();
            String rut = sessionData.get("userRut").toString();
            if (!"ADMINISTRATIVE".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene el rol necesario");
            }

            // Decodificar la imagen Base64 si existe
            if (newsDTO.getPrimaryImage() != null && !newsDTO.getPrimaryImage().isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(newsDTO.getPrimaryImage());
                String imageName = UUID.randomUUID().toString() + ".jpg";

                // Guardar la imagen
                Path imagePath = Paths.get("Polo/src/main/resources/static/images/", imageName);
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageBytes);

                // Asignar el nombre de la imagen a la noticia
                newsDTO.setPrimaryImage(imageName);
            }

            // Actualizar la noticia
            boolean updated = newsService.updateNews(newsDTO, rut);
            String tipo = "noticia";
            changesController.createChange(payload, tipo);

            if (updated) {
                System.out.println("NOTICIA ACTUALIZADA");
                return ResponseEntity.status(HttpStatus.OK).body("Noticia actualizada");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar la noticia");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

}
