package xyz.qianbidao.springcloudevents.springenventrw.eventlistener;

import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;
import xyz.qianbidao.springcloudevents.springenventrw.eventhandle.EventHandle;
import xyz.qianbidao.springcloudevents.springenventrw.sender.CloudEventSender;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 */
public class CloudEventListener {

    @Autowired
    private ApplicationContext applicationContext;
    private Collection<EventHandle> eventHandles;

    @PostConstruct
    public void init(){
        eventHandles = applicationContext.getBeansOfType(EventHandle.class).values();
    }

    public void handle(CloudEvent e){
        eventHandles.forEach(eventHandle -> {
            if (eventHandle.getKey() != null) {
                if(eventHandle.getKey().equals(e.getType())){
                    if (null == e.getDataContentType()) {
                        eventHandle.handle(e);
                    }else {
                        eventHandle.handle(JSON.parseObject(new String(e.getData().toBytes())).toJavaObject(eventHandle.getType()));
                    }
                }
            }else if (eventHandle.getType() != null) {
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(e.getType());
                } catch (ClassNotFoundException ex) {
                    return;
                }
                if (clazz.isAssignableFrom(eventHandle.getType())) {
                    if (null == e.getDataContentType()) {
                        eventHandle.handle(e);
                    } else {
                        eventHandle.handle(JSON.parseObject(new String(e.getData().toBytes())).toJavaObject(clazz));
                    }
                }
            }

        });
    }

}
