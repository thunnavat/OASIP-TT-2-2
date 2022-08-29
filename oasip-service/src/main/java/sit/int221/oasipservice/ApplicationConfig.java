package sit.int221.oasipservice;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "PUT", "DELETE", "POST");
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder() { return new Argon2PasswordEncoder(); }
}
