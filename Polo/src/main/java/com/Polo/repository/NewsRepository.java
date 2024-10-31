package com.Polo.repository;

import com.Polo.model.News;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    Optional<News> findByNewsTitle(String newsTitle);
    List<News> findByNewsCategory(String newsCategory);

}
