package com.Polo.Details;

import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String rut) throws UsernameNotFoundException {
        User user = userRepository.findByUserRut(rut);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con rut: " + rut);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserRut(), user.getUserPassword(), new ArrayList<>());
    }
}
