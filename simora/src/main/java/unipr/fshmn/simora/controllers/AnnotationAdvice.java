package unipr.fshmn.simora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import unipr.fshmn.simora.db.User;
import unipr.fshmn.simora.db.UserRepository;

@ControllerAdvice(annotations = Controller.class)
public class AnnotationAdvice {

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        return userRepository.findById(Long.valueOf(userDetails.getUsername())).get();
    }
}