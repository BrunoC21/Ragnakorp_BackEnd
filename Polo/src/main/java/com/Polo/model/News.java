package com.Polo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_code", nullable = false)
    private Integer id;

    @Basic

    @Column(name = "news_title", nullable = false)
    private String newsTitle;

    @Column(name = "news_content", columnDefinition = "TEXT", nullable = false)
    private String newsContent;

    @CreationTimestamp
    @Column(name = "news_date", nullable = false, updatable = false)
    private LocalDateTime newsDateTime;

    @Column(name = "news_secondaryimage", nullable = false)
    private String secondaryImage;

    @Column(name = "news_category", nullable = false)
    private String newsCategory;

    @Column(name = "news_writer", nullable = false)
    private String newsWriter;

    @Column(name = "news_picture", nullable = false)
    private String newsPicture;

    @Column(name = "news_primaryimage", nullable = false)
    private String primaryImage;

    // Relacion ManyToMany con User
    @ManyToMany(mappedBy = "news")
    private List<User> user;

    // Relacion ManyToMany con changes
    @ManyToMany(mappedBy = "newsChange")
    private List<Changes> changes;

}
