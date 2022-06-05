package xyz.qianbidao.springcloudevents.springenventrw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
@ConfigurationProperties("cloudevent")
@Component
@Data
public class EventsConfig {
    private String applicationSource;
    private String brokerUrl;

}
