package xyz.qianbidao.springcloudevents.springenventrw.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;
import xyz.qianbidao.springcloudevents.springenventrw.eventhandle.EventHandle;
import xyz.qianbidao.springcloudevents.springenventrw.eventlistener.RabbimtMQCloudEventListener;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @auther 铅笔刀
 * @date 2022/11/30
 */
@Configuration
public class CLoudEventListenerConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private GenericApplicationContext applicationContext;
    @Resource
    private TopicExchange cloudEventExchange;

    @Autowired
    private EventsConfig eventsConfig;



    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public List<Queue> listenerQueues(){
        Map<String, EventHandle> beansOfType = applicationContext.getBeansOfType(EventHandle.class);
        Collection<EventHandle> eventHandles = beansOfType.values();
        List<Queue> list=new LinkedList();
        for (EventHandle eventHandle : eventHandles) {
            String handleKey = eventHandle.getKey();
            Queue queue = QueueBuilder
                    .durable(eventsConfig.getApplicationSource() + SpringCloudEventsConstant.DIVISION + handleKey)
                    .autoDelete()
                    .build();
            list.add(queue);
            amqpAdmin.declareQueue(queue);
            //不指定作用域方式
        }
        return list;
    }

    @Bean
    public List<Binding> queueBindings(List<Queue> listenerQueues){
        List<Binding> result = new LinkedList();
        for (Queue listenerQueue : listenerQueues) {
            Binding binding = BindingBuilder.bind(listenerQueue)
                    .to(cloudEventExchange)
                    .with(listenerQueue.getName().replaceFirst(eventsConfig.getApplicationSource()+ SpringCloudEventsConstant.DIVISION,""));
            result.add(binding);
            amqpAdmin.declareBinding(binding);
        }
        return result;
    }

    @Bean
    public RabbitListenerConfigurer rabbimtMQCloudEventListener(List<Queue> listenerQueues){
        RabbimtMQCloudEventListener rabbimtMQCloudEventListener = new RabbimtMQCloudEventListener();
        rabbimtMQCloudEventListener.setListenerQueues(listenerQueues);
        return rabbimtMQCloudEventListener;
    }
}
