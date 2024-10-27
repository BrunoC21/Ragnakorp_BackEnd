package com.Polo.repository;

import com.Polo.model.News;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    News findByNewsTitle(String newsTitle);
    News findByNewsCategory(String newsCategory);

}
