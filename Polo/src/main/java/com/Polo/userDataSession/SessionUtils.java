package com.Polo.userDataSession;

import com.Polo.model.UserDTO;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionUtils {

    public static void setUserSession(UserDTO userDTO, String rut, HttpSession session) {
        session.setAttribute("userRut", rut);
        session.setAttribute("username", userDTO.getUserName());
        session.setAttribute("lastName", userDTO.getUserLastName());
        session.setAttribute("role", userDTO.getUserRole());
        session.setAttribute("email", userDTO.getUserEmail());
        session.setAttribute("phone", userDTO.getUserPhone());

        getUserSession(session);
    }
    

    public static Map<String, Object> getUserSession(HttpSession session) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("userRut", session.getAttribute("userRut"));
        sessionData.put("username", session.getAttribute("username"));
        sessionData.put("lastName", session.getAttribute("lastName"));
        sessionData.put("role", session.getAttribute("role"));
        sessionData.put("email", session.getAttribute("email"));
        sessionData.put("phone", session.getAttribute("phone"));

        System.out.println(" ");
        System.out.println(sessionData);
        return sessionData;
    }
}

