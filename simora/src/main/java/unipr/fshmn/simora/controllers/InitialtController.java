package unipr.fshmn.simora.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class InitialtController {

    @RequestMapping("/addUser")
    public String addUser()
    {
        return "addUser";
    }

    @RequestMapping("/addSchedule")
    public String addSchedule()
    {
        return "addSchedule";
    }
    @RequestMapping("/videoCall")
    public String videoCall()
    {
        return "videoCall";
    }

    @RequestMapping("")
    public String start() {return "index";}


    @RequestMapping("/")
    public String index() {return "index";}
}
