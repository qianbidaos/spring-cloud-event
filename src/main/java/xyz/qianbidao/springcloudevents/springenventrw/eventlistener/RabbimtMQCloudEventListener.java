package xyz.qianbidao.springcloudevents.springenventrw.eventlistener;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;


/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
@Slf4j
@RabbitListener
public class RabbimtMQCloudEventListener extends CloudEventListener{


    @RabbitListener(bindings = {@QueueBinding
            (value = @Queue(value = "sdsa",durable = "true",autoDelete = "true"),
                    exchange = @Exchange(value=SpringCloudEventsConstant.CLOUD_EVENT_EXCHANGE,type = ExchangeTypes.TOPIC),
                    key = "xyz.qianbidao.events.#")})
    public void process(byte[] message) {
        CloudEvent cloudEvent = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE)
                .deserialize(message);
        handle(cloudEvent);
    }

}
