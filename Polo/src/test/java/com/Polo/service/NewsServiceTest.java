package com.Polo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Polo.Details.NewsUserDetailsService;
import com.Polo.model.News;
import com.Polo.model.NewsDTO;
import com.Polo.model.NewsMapper;
import com.Polo.repository.NewsRepository;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsUserDetailsService newsUserDetailsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper = Mappers.getMapper(NewsMapper.class);

    @InjectMocks
    private NewsService newsService;

    // Test para crear una noticia
    @Test
    void siNoticiaEsValidaDebeCrearNoticia() {
        // Arrange
        News news = new News();
        news.setNewsTitle("Noticia de ejemplo");
        news.setNewsContent("Contenido de la noticia");

        when(newsRepository.save(news)).thenReturn(news);
        // // Simulamos el comportamiento de saveDetails para evitar el NullPointerException
        // when(newsUserDetailsService.saveDetails(news, "11111111-1")).thenReturn(true);

        // Act
        boolean resultado = newsService.createNews(news, "12345678-9");

        // Assert
        assertTrue(resultado); // La noticia debe ser creada
        verify(newsRepository, times(1)).save(news); // Verifica que se llame a save
        verify(newsUserDetailsService, times(1)).saveDetails(news, "12345678-9"); // Verifica que se llame a saveDetails
    }

    // Test para borrar una noticia existente
    @Test
    void siNoticiaExisteDebeBorrarNoticia() {
        // Arrange
        int newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(true);

        // Act
        boolean resultado = newsService.deleteNews(newsId);

        // Assert
        assertTrue(resultado); // La noticia debe ser borrada
        verify(newsRepository, times(1)).deleteById(newsId); // Verifica que se llame a deleteById
    }

    // Test para borrar una noticia inexistente
    @Test
    void siNoticiaNoExisteNoDebeBorrarNoticia() {
        // Arrange
        int newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(false);

        // Act
        boolean resultado = newsService.deleteNews(newsId);

        // Assert
        assertFalse(resultado); // La noticia no debe ser borrada
        verify(newsRepository, times(0)).deleteById(newsId); // No se debe llamar a deleteById
    }

    // Test para buscar todas las noticias
    @Test
    void debeRetornarListaDeNoticias() {
        // Arrange
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        lenient().when(newsRepository.findAll()).thenReturn(newsList); // Hacemos el stubbing de manera "lenient"

        List<NewsDTO> newsDTOList = new ArrayList<>();
        newsDTOList.add(new NewsDTO());
        lenient().when(newsMapper.newsListToNewsDTOList(newsList)).thenReturn(newsDTOList); // Hacemos el stubbing de manera "lenient"

        // Act
        List<NewsDTO> resultado = newsService.findAllNews();

        // Assert
        assertEquals(newsDTOList, resultado); // Verifica que se retorne la lista de noticias
    }

    // Test para buscar noticia por ID
    @Test
    void siNoticiaExisteDebeRetornarNoticiaPorId() {
        // Arrange
        News news = new News();
        news.setId(1);
        news.setNewsTitle("Noticia de ejemplo");
        news.setNewsContent("Contenido de la noticia");

        when(newsRepository.findById(1)).thenReturn(Optional.of(news));

        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(1);
        newsDTO.setNewsTitle("Noticia de ejemplo");
        newsDTO.setNewsContent("Contenido de la noticia");

        Mockito.lenient().when(newsMapper.newsToNewsDTO(news)).thenReturn(newsDTO);

        // Act
        Optional<NewsDTO> resultado = newsService.findNewsById(1);

        // Assert
        assertTrue(resultado.isPresent()); // La noticia debe existir
        assertEquals(newsDTO, resultado.get()); // El resultado debe coincidir con el DTO esperado
    }

    // Test para buscar noticia por título
    @Test
    void siNoticiaExisteDebeRetornarNoticiaPorTitulo() {
        // Arrange
        News news = new News();
        news.setNewsTitle("Noticia de ejemplo");
        news.setNewsContent("Contenido de la noticia");

        when(newsRepository.findByNewsTitle("Noticia de ejemplo")).thenReturn(Optional.of(news));

        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsTitle("Noticia de ejemplo");
        newsDTO.setNewsContent("Contenido de la noticia");

        Mockito.lenient().when(newsMapper.newsToNewsDTO(news)).thenReturn(newsDTO);

        // Act
        Optional<NewsDTO> resultado = newsService.findNewsByTitle("Noticia de ejemplo");

        // Assert
        assertTrue(resultado.isPresent()); // La noticia debe existir
        assertEquals(newsDTO, resultado.get()); // El resultado debe coincidir con el DTO esperado
    }

    // Test para buscar noticias por categoría
    @Test
    void siNoticiasExistenDebeRetornarListaPorCategoria() {
        // Arrange
        String categoria = "Tecnología";
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        when(newsRepository.findByNewsCategory(categoria)).thenReturn(newsList);

        List<NewsDTO> newsDTOList = new ArrayList<>();
        newsDTOList.add(new NewsDTO());
        Mockito.lenient().when(newsMapper.newsListToNewsDTOList(newsList)).thenReturn(newsDTOList);

        // Act
        Optional<List<NewsDTO>> resultado = newsService.findNewsByCategory(categoria);

        // Assert
        assertTrue(resultado.isPresent()); // La lista de noticias debe existir
        assertEquals(newsDTOList, resultado.get()); // El resultado debe coincidir con la lista de DTOs esperada
    }

}
