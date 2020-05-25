package unipr.fshmn.simora;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/recoveryEmail").setViewName("recoveryEmail");
        registry.addViewController("/recoveryCode").setViewName("recoveryCode");
        registry.addViewController("/recoveryPassword").setViewName("recoveryPassword");
    }
}