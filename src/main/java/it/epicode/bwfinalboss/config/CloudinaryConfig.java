package it.epicode.bwfinalboss.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "tuo-cloud-name");
        config.put("api_key", "tua-api-key");
        config.put("api_secret", "tua-api-secret");
        return new Cloudinary(config);
    }
}
