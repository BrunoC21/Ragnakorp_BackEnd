package com.Polo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.Polo.Details.NewsUserDetailsService;
import com.Polo.model.News;
import com.Polo.model.NewsDTO;
import com.Polo.model.NewsMapper;
import com.Polo.model.Suscription;
import com.Polo.repository.NewsRepository;
import com.Polo.repository.SuscriptionRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final SuscriptionRepository suscriptionRepository;
    private final NewsUserDetailsService newsUserDetailsService;
    private final EmailService emailService;

    private final NewsMapper mapper = Mappers.getMapper(NewsMapper.class);

    public boolean createNews(News news, String userRut) {
        if (news != null) {
            newsRepository.save(news);
            newsUserDetailsService.saveDetails(news, userRut);
            sendNewsNotificationToSubscribers(news);
            return true;
        } else {
            System.out.println("Error al crear la noticia");
            return false;
        }
    }

    private void sendNewsNotificationToSubscribers(News news) {
        List<Suscription> suscriptions = suscriptionRepository.findAll();

        for (Suscription suscription : suscriptions) {
            String subject = "Nueva noticia publicada: " + news.getNewsTitle();
            String body = "<h1>" + news.getNewsTitle() + "</h1>"
                    + "<p>" + news.getNewsContent() + "</p>"
                    + "<p><strong>Categoría:</strong> " + news.getNewsCategory() + "</p>"
                    + "<p><a href='http://localhost:8080/proyecto/news/" + news.getId() + "'>Leer más</a></p>"
                    + "<p><a href='http://localhost:8080/proyecto/suscriptor/unsubscribe?email="
                    + suscription.getSubEmail()
                    + "'>Darse de baja</a></p>";
            try {
                emailService.sendEmail(suscription.getSubEmail(), subject, body);
            } catch (MessagingException e) {
                System.out.println("Error al enviar correo a: " + suscription.getSubEmail());
                e.printStackTrace();
            }
        }
    }

    public boolean deleteNews(int id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            System.out.println("Eliminado");
            return true;
        }
        System.out.println("No eliminado");
        return false;
    }

    public List<NewsDTO> findAllNews() {
        List<News> newsList = newsRepository.findAll();
        List<NewsDTO> newsDTOList;
        newsDTOList = mapper.newsListToNewsDTOList(newsList);
        System.out.println("Encontrados");
        return newsDTOList;
    }

    public Optional<NewsDTO> findNewsById(int id) {
        Optional<News> optional = newsRepository.findById(id);
        if (optional.isPresent()) {
            System.out.println("Encontrado");
            return Optional.of(mapper.newsToNewsDTO(optional.get()));
        }
        System.out.println("No encontrado");
        return Optional.empty();
    }

    public Optional<NewsDTO> findNewsByTitle(String newsTitle) {
        Optional<News> optional = newsRepository.findByNewsTitle(newsTitle);
        if (optional.isPresent()) {
            System.out.println("Encontrado");
            return Optional.of(mapper.newsToNewsDTO(optional.get()));
        }
        System.out.println("No encontrado");
        return Optional.empty();
    }

    public Optional<List<NewsDTO>> findNewsByCategory(String newsCategory) {
        List<News> newsList = newsRepository.findByNewsCategory(newsCategory);
        if (newsList != null && !newsList.isEmpty()) {
            System.out.println("Encontrado");
            return Optional.of(newsList.stream()
                    .map(mapper::newsToNewsDTO)
                    .collect(Collectors.toList()));
        }
        System.out.println("No encontrado");
        return Optional.empty();
    }

    public boolean updateNews(NewsDTO newsDTO, String userRut) {
        if (newsDTO != null && newsDTO.getId() != null) {
            Optional<News> optionalNews = newsRepository.findById(newsDTO.getId());
            if (optionalNews.isPresent()) {
                News news = optionalNews.get();

                // Actualizar los campos de la noticia
                news.setNewsTitle(newsDTO.getNewsTitle());
                news.setNewsContent(newsDTO.getNewsContent());

                System.out.println(newsDTO.getNewsContent());

                news.setNewsWriter(newsDTO.getNewsWriter());
                news.setNewsCategory(newsDTO.getNewsCategory());

                // Si hay una nueva imagen, actualizamos
                if (newsDTO.getPrimaryImage() != null && !newsDTO.getPrimaryImage().isEmpty()) {
                    news.setPrimaryImage(newsDTO.getPrimaryImage());
                }

                // Guardar la noticia actualizada
                newsRepository.save(news);
                newsUserDetailsService.saveDetails(news, userRut);
                return true;
            }
            System.out.println("Noticia no encontrada");
        }
        System.out.println("Datos de la noticia incorrectos");
        return false;
    }

}
