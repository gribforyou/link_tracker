package backend.academy.bot.scrapper.communication;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:scrapper.properties")
public class ScrapperConfig {
    @Value("${scrapper.host}")
    private String scrapperHost;

    @Value("${scrapper.port}")
    private int scrapperPort;
}
