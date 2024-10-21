package com.Polo.Details;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.Polo.model.News;
import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsUserDetailsService {

    private final UserRepository userRepository;

    public void saveDetails(News news) {
        String newsWriter = news.getNewsWriter();

        // Buscar el usuario por su nombre
        User user = userRepository.findByUserName(newsWriter);
        if (user != null) {
            
            // Agregar la noticia a la lista de noticias del usuario
            if (user.getNews() == null) {
                user.setNews(new ArrayList<>()); // Inicializa la lista si es nula
            }

            // Crear una nueva instancia de News o usar la existente
            if (!user.getNews().contains(news)) {
                user.getNews().add(news); // Agregar la noticia a la lista del usuario
            }

            // Guardar el usuario para actualizar la relación en la base de datos
            userRepository.save(user);
        } else {
            System.out.println("Usuario no encontrado con el nombre: " + newsWriter);
        }
    }
}

