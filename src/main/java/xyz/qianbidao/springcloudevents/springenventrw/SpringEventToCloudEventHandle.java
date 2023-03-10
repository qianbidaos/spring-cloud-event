package xyz.qianbidao.springcloudevents.springenventrw;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.MimeTypeUtils;
import xyz.qianbidao.springcloudevents.springenventrw.config.EventsConfig;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SystemEvent;
import xyz.qianbidao.springcloudevents.springenventrw.sender.CloudEventSender;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Collection;

/**
 * @auther éįŽå
 * @date 2022/5/22
 */
@Slf4j
public class SpringEventToCloudEventHandle implements ApplicationListener {
    @Autowired
    private EventsConfig eventsConfig;

    @Autowired
    private ApplicationContext applicationContext;
    private Collection<CloudEventSender> cloudEventSenders;


    @PostConstruct
    public void init(){
        cloudEventSenders = applicationContext.getBeansOfType(CloudEventSender.class).values();
    }

    byte[] toPrimitives(Byte[] oBytes){

        byte[] bytes = new byte[oBytes.length];
        for(int i = 0; i < oBytes.length; i++){
            bytes[i] = oBytes[i];
        }
        return bytes;

    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if(event instanceof SystemEvent){
            return;
        }

        io.cloudevents.core.v1.CloudEventBuilder cloudEventBuilder = CloudEventBuilder.v1()
                .withSource(new URI(eventsConfig.getApplicationSource()))
                .withType(event.getClass().getName())
                .withId(IdUtil.objectId());

        if(event instanceof ApplicationCloudJSONDataEvent){
            String data = ((ApplicationCloudJSONDataEvent) event).getData();
            if (null != data) {
                cloudEventBuilder
                    .withData(data.getBytes(StandardCharsets.UTF_8))
                    .withDataContentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                ;
            }
        }else {
            cloudEventBuilder.withDataContentType(null);
        }
        CloudEvent cloudEvent = cloudEventBuilder
                .withSubject("qianbidao boot event")
                .withTime(OffsetDateTime.now())
                .build();
        cloudEventSenders.forEach(cloudEventSender -> {
            log.debug("log debug send event {} {}",cloudEventSender.getClass().getName() , cloudEvent);
            try {
                cloudEventSender.send(cloudEvent);
            }catch (Exception e){}
        });
    }
}
