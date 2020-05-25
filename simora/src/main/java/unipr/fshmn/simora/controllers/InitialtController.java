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

    @RequestMapping("/indexA")
    public String indexA()
    {
        return "indexA";
    }


    @RequestMapping("/indexP")
    public String indexP() {
        return "indexP";
    }
}
