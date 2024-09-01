package fr.univ.lille.referencement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ChiffrementConfig {

    @Profile("Hmac")
    @Bean
    public Encoder chiffrementServiceHmac() {
        return new Hmac();
    }

    @Profile("MD5")
    @Bean
    public Encoder chiffrementServiceMD5() {
        return new MD5();
    }
}
