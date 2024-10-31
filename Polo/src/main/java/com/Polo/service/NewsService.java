package com.Polo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.Polo.Details.NewsUserDetailsService;
import com.Polo.model.*;
import com.Polo.repository.NewsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsUserDetailsService newsUserDetailsService;

    private final NewsMapper mapper = Mappers.getMapper(NewsMapper.class);

    public boolean createNews(News news, String userRut) {
        if (news != null) {
            newsRepository.save(news);
            newsUserDetailsService.saveDetails(news, userRut);
            return true;
        } else {
            System.out.println("Error al crear la noticia");
            return false;
        }
    }

    public boolean deleteNews(int id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<NewsDTO> findAllNews() {
        List<News> newsList = newsRepository.findAll();
        List<NewsDTO> newsDTOList;
        newsDTOList = mapper.newsListToNewsDTOList(newsList);
        return newsDTOList;
    }

    public Optional<NewsDTO> findNewsById(int id) {
        Optional<News> optional = newsRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(mapper.newsToNewsDTO(optional.get()));
        }
        return Optional.empty();
    }

    public Optional<NewsDTO> findNewsByTitle(String newsTitle) {
        Optional<News> optional = newsRepository.findByNewsTitle(newsTitle);
        if (optional.isPresent()) {
            return Optional.of(mapper.newsToNewsDTO(optional.get()));
        }
        return Optional.empty();
    }

    public Optional<List<NewsDTO>> findNewsByCategory(String newsCategory) {
        List<News> newsList = newsRepository.findByNewsCategory(newsCategory);
        if (newsList != null && !newsList.isEmpty()) {
            return Optional.of(newsList.stream()
                                    .map(mapper::newsToNewsDTO)
                                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }


}
