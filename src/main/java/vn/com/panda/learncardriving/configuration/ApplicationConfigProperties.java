package vn.com.panda.learncardriving.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationConfigProperties {
    private String jwtSecret;
    private Long jwtExpired;
}
