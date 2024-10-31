package com.Polo.model;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO newsToNewsDTO(News news);
    News newsDTOToNews(NewsDTO newsDTO);
    List<NewsDTO> newsListToNewsDTOList(List<News> news);
    List<News> newsDTOListToNewsList(List<NewsDTO> newsDTOs);
}
