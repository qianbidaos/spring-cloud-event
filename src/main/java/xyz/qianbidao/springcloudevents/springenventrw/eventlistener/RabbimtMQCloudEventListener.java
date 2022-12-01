package xyz.qianbidao.springcloudevents.springenventrw.eventlistener;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.qianbidao.springcloudevents.springenventrw.config.EventsConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
@Slf4j
@Data
public class RabbimtMQCloudEventListener extends CloudEventListener implements RabbitListenerConfigurer{

    private List<Queue> listenerQueues;


    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        for (Queue queue : listenerQueues) {
            //使用适配器来处理消息，设置了order，pay队列的消息处理逻辑
            SimpleRabbitListenerEndpoint endpoint2 = new SimpleRabbitListenerEndpoint();
            String queueName = queue.getName();
            endpoint2.setId(queueName);
            endpoint2.setQueues(queue);
            endpoint2.setMessageListener(message->{
                CloudEvent cloudEvent = EventFormatProvider
                        .getInstance()
                        .resolveFormat(JsonFormat.CONTENT_TYPE)
                        .deserialize(message.getBody());
                handle(cloudEvent,message.getMessageProperties().getConsumerQueue());
            });
            rabbitListenerEndpointRegistrar.registerEndpoint(endpoint2);
        }
    }



}
