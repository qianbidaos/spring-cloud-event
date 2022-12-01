package xyz.qianbidao.springcloudevents.springenventrw.eventlistener;

import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import xyz.qianbidao.springcloudevents.springenventrw.config.EventsConfig;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;
import xyz.qianbidao.springcloudevents.springenventrw.eventhandle.EventHandle;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
public class CloudEventListener {

    @Autowired
    private ApplicationContext applicationContext;
    protected Collection<EventHandle> eventHandles;

    @Autowired
    private EventsConfig eventsConfig;

    @PostConstruct
    public void init(){
        eventHandles = applicationContext.getBeansOfType(EventHandle.class).values();
    }

    public void handle(CloudEvent e, String consumerQueueName){
        eventHandles.forEach(eventHandle -> {
            if (eventHandle.getKey() != null) {
                if(eventHandle.getKey().equals(consumerQueueName.replaceFirst(eventsConfig.getApplicationSource()+ SpringCloudEventsConstant.DIVISION,""))){
                    eventHandle.handle(e);
                }
            }else if (eventHandle.getType() != null) {
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(e.getType());
                } catch (ClassNotFoundException ex) {
                    return;
                }
                if (clazz.isAssignableFrom(eventHandle.getType())) {
                    eventHandle.handle(e);
                }
            }

        });
    }

}
