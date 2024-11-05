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

import static org.mockito.Mockito.doNothing;
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

    @Test
    void siNoticiaEsValidaDebeCrearNoticia() {
        News news = new News();
        news.setNewsTitle("Noticia de ejemplo");
        news.setNewsContent("Contenido de la noticia");

        when(newsRepository.save(news)).thenReturn(news);
        doNothing().when(newsUserDetailsService).saveDetails(news, "11111111-1");

        boolean resultado = newsService.createNews(news, "11111111-1");

        assertTrue(resultado);
        verify(newsRepository, times(1)).save(news);
        verify(newsUserDetailsService, times(1)).saveDetails(news, "11111111-1");

        System.out.println("Test siNoticiaEsValidaDebeCrearNoticia pasó exitosamente.");
    }

    @Test
    void siNoticiaExisteDebeBorrarNoticia() {
        int newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(true);

        boolean resultado = newsService.deleteNews(newsId);

        assertTrue(resultado);
        verify(newsRepository, times(1)).deleteById(newsId);

        System.out.println("Test siNoticiaExisteDebeBorrarNoticia pasó exitosamente.");
    }

    @Test
    void siNoticiaNoExisteNoDebeBorrarNoticia() {
        int newsId = 1;
        when(newsRepository.existsById(newsId)).thenReturn(false);

        boolean resultado = newsService.deleteNews(newsId);

        assertFalse(resultado);
        verify(newsRepository, times(0)).deleteById(newsId);

        System.out.println("Test siNoticiaNoExisteNoDebeBorrarNoticia pasó exitosamente.");
    }

    @Test
    void debeRetornarListaDeNoticias() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        lenient().when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsDTO> newsDTOList = new ArrayList<>();
        newsDTOList.add(new NewsDTO());
        lenient().when(newsMapper.newsListToNewsDTOList(newsList)).thenReturn(newsDTOList);

        List<NewsDTO> resultado = newsService.findAllNews();

        assertEquals(newsDTOList, resultado);
        System.out.println("Test debeRetornarListaDeNoticias pasó exitosamente.");
    }

    @Test
    void siNoticiaExisteDebeRetornarNoticiaPorId() {
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

        Optional<NewsDTO> resultado = newsService.findNewsById(1);

        assertTrue(resultado.isPresent());
        assertEquals(newsDTO, resultado.get());

        System.out.println("Test siNoticiaExisteDebeRetornarNoticiaPorId pasó exitosamente.");
    }

    @Test
    void siNoticiaExisteDebeRetornarNoticiaPorTitulo() {
        News news = new News();
        news.setNewsTitle("Noticia de ejemplo");
        news.setNewsContent("Contenido de la noticia");

        when(newsRepository.findByNewsTitle("Noticia de ejemplo")).thenReturn(Optional.of(news));

        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsTitle("Noticia de ejemplo");
        newsDTO.setNewsContent("Contenido de la noticia");

        Mockito.lenient().when(newsMapper.newsToNewsDTO(news)).thenReturn(newsDTO);

        Optional<NewsDTO> resultado = newsService.findNewsByTitle("Noticia de ejemplo");

        assertTrue(resultado.isPresent());
        assertEquals(newsDTO, resultado.get());

        System.out.println("Test siNoticiaExisteDebeRetornarNoticiaPorTitulo pasó exitosamente.");
    }

    @Test
    void siNoticiasExistenDebeRetornarListaPorCategoria() {
        String categoria = "Tecnología";
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        when(newsRepository.findByNewsCategory(categoria)).thenReturn(newsList);

        List<NewsDTO> newsDTOList = new ArrayList<>();
        newsDTOList.add(new NewsDTO());
        Mockito.lenient().when(newsMapper.newsListToNewsDTOList(newsList)).thenReturn(newsDTOList);

        Optional<List<NewsDTO>> resultado = newsService.findNewsByCategory(categoria);

        assertTrue(resultado.isPresent());
        assertEquals(newsDTOList, resultado.get());

        System.out.println("Test siNoticiasExistenDebeRetornarListaPorCategoria pasó exitosamente.");
    }
}
