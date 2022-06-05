package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;

@Slf4j
public class CloudEventHandle implements EventHandle<ApplicationCloudJSONDataEvent>{


    @Override
    public Class getType() {
        return ApplicationCloudJSONDataEvent.class;
    }

    @Override
    public void handle(ApplicationCloudJSONDataEvent cloudEvent) {
        log.info("cloudEvent {}",cloudEvent);
    }
}
