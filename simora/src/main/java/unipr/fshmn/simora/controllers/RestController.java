package unipr.fshmn.simora.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/addUser")
    public ModelAndView addUser()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addUser");
        return mv;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }
}
