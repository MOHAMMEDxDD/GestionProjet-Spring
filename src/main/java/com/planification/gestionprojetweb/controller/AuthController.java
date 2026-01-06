package com.planification.gestionprojetweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Oula Repository direct
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.planification.gestionprojetweb.model.User;
import com.planification.gestionprojetweb.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService; // Ola Repository

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session, 
                        Model model) {
        
        User user = userService.authenticate(username, password); 

        if (user != null) {
            session.setAttribute("user", user); 
            
            
            if ("RESPONSABLE".equals(user.getRole())) {
                return "redirect:/dashboard-responsable";
            } else {
                return "redirect:/dashboard-developpeur";
            }
        } else {
            model.addAttribute("error", "Identifiants incorrects");
            return "login";
        }
    }
}