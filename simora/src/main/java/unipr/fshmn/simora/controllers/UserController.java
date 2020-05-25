package unipr.fshmn.simora.controllers;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import unipr.fshmn.simora.db.User;
import unipr.fshmn.simora.db.UserRepository;
import unipr.fshmn.simora.mail.EmailServiceImpl;

import static org.passay.NumberRangeRule.ERROR_CODE;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;
    @RequestMapping(path="/add")
    public ModelAndView addNewUser (@RequestParam String name,@RequestParam String lastName,@RequestParam String department
            , @RequestParam String email,@RequestParam Long id) {

        String randomPassword = generatePassayPassword();
        User n = new User();
        n.setID(id);
        n.setFirstName(name);
        n.setEmail(email);
        n.setPassword(passwordEncoder.encode(randomPassword));
        n.setLastName(lastName);
        n.setEnabled(Boolean.TRUE);
        n.setDepartment(department);
        userRepository.save(n);
        ModelAndView modelAndView=new ModelAndView();
        //Me ja qu ni email me passwordin nashta
       // modelAndView.setViewName("indexA");
        modelAndView.setViewName("userAdded");
        return modelAndView;
    }

    @GetMapping(path="/sendEmail")
    public ModelAndView addNewUser (@RequestParam String to) {
        emailService.sendSimpleMessage(to,"Test","qkemi");
        return null;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    private String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }
}