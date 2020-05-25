package unipr.fshmn.simora;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/addUser").setViewName("addUser");
        registry.addViewController("/addSchedule").setViewName("addSchedule");
        registry.addViewController("/indexA").setViewName("indexA");
        //registry.addViewController("/indexP").setViewName("indexP");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/recoveryEmail").setViewName("recoveryEmail");
        registry.addViewController("/recoveryCode").setViewName("recoveryCode");
        registry.addViewController("/recoveryPassword").setViewName("recoveryPassword");
    }
}