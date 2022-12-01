package xyz.qianbidao.springcloudevents.springenventrw.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.qianbidao.springcloudevents.springenventrw.SpringEventToCloudEventHandle;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;
import xyz.qianbidao.springcloudevents.springenventrw.sender.RabbitMQSender;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
@Configuration
@ComponentScan("xyz.qianbidao.springcloudevents.springenventrw.**")
public class CloudEventSendConfig {

    @Bean
    @ConditionalOnClass(value = RabbitTemplate.class)
    public RabbitMQSender rabbitMQSender(){
        return new RabbitMQSender();
    }


    @Bean
    @ConditionalOnClass(value = RabbitTemplate.class)
    public TopicExchange cloudEventExchange() {
        return new TopicExchange(SpringCloudEventsConstant.CLOUD_EVENT_EXCHANGE, true, false);
    }

    @Bean
    @ConditionalOnProperty("cloudevent.applicationSource")
    public SpringEventToCloudEventHandle springEventToCloudEventHandle(){
        return new SpringEventToCloudEventHandle();
    }


}
