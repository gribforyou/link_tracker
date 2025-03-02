package backend.academy.scrapper.update_sender;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:update_sender.properties")
public class UpdateSenderConfig {
    @Value("${bot.url}")
    String botUrl;

    @Value("${bot.port}")
    int port;
}
