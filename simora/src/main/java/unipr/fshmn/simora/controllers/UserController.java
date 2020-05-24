package unipr.fshmn.simora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import unipr.fshmn.simora.db.User;
import unipr.fshmn.simora.db.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path="/add") // Map ONLY POST Requests
    public ModelAndView addNewUser (@RequestParam String name,@RequestParam String lastName,@RequestParam String department
            , @RequestParam String email,@RequestParam Long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setID(id);
        n.setFirstName(name);
        n.setEmail(email);
        n.setLastName(lastName);
        n.setDepartment(department);
        userRepository.save(n);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("indexA");
        return modelAndView;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}