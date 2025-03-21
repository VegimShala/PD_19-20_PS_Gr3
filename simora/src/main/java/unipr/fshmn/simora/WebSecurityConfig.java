package unipr.fshmn.simora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/js/**","/images/**").permitAll()
                .antMatchers("/addUser").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/addSchedule").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/add").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/schedule/add").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/schedule/remove").access("hasRole('ROLE_ADMIN')")//vetem roli i adminit ka te drejte te hyje ne keto faqe
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT ID, password, enabled from user where ID = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.ID, IF(u.is_admin,\"ROLE_ADMIN\",\"ROLE_USER\") \n" +
                                "                                FROM user u \n" +
                                "                                WHERE u.ID= ?"
                );
    }
}