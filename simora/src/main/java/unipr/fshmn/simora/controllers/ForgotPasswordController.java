package unipr.fshmn.simora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import unipr.fshmn.simora.db.User;
import unipr.fshmn.simora.db.UserRepository;
import unipr.fshmn.simora.mail.EmailServiceImpl;

@Controller
public class ForgotPasswordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @RequestMapping(path="/sendRecoveryEmail")
    public ModelAndView addNewUser (@RequestParam Long id) {
        if(userRepository.findById(id).isPresent()){
        User n = userRepository.findById(id).get();
        String random = ""+(long) (Math.random()*10000);
        while(random.length()!=5){
            random+="0";
        }
        n.setCode(random);
        userRepository.save(n);
        emailService.sendSimpleMessage(n.getEmail(),"SIMORA - Keni harruar fjalekalimin","Kodi per harrimin e fjalekalimit eshte: " +random+
                ". Nese nuk keni kerkuar nderrim te fjalekalimit na kontaktoni simora-ndihme@uni-pr.edu ." );
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("recoveryCode");
        return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("recoveryEmail?error");
            return modelAndView;
        }
    }

    @RequestMapping(path="/setPassword")
    public ModelAndView recoveryCode (@RequestParam String password, @RequestParam Long id, @RequestParam String repeatPassword) {
        if(!userRepository.findById(id).isPresent()&&password.equals(repeatPassword)){
            User n = userRepository.findById(id).get();
            n.setPassword(password);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("id",n.getID());
            modelAndView.setViewName("login");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("recoveryPassword?error");
            return modelAndView;
        }
    }
    @RequestMapping(path="/checkPassword")
    public ModelAndView recoveryCode (@RequestParam String code) {
        if(!userRepository.findByCode(code).isEmpty()){
            User n = userRepository.findByCode(code).get(0);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("id",n.getID());
            modelAndView.setViewName("recoveryCode");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("recoveryEmail?error");
            return modelAndView;
        }
    }
}

