package xyz.qianbidao.springcloudevents.springenventrw.sender;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import xyz.qianbidao.springcloudevents.springenventrw.config.EventsConfig;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;

import javax.annotation.Resource;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
@Slf4j
public class RabbitMQSender implements CloudEventSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private EventsConfig eventsConfig;

    @Override
    public void send(CloudEvent cloudEvent) {
        rabbitTemplate.convertAndSend(SpringCloudEventsConstant.CLOUD_EVENT_EXCHANGE, eventsConfig.getApplicationSource() + SpringCloudEventsConstant.DIVISION + cloudEvent.getType(),
                new Message(
                        EventFormatProvider
                                .getInstance()
                                .resolveFormat(JsonFormat.CONTENT_TYPE)
                                .serialize(cloudEvent))
        );
    }
}
