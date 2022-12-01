package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;

@Slf4j
public class CloudEventHandle implements EventHandle<CloudEvent>{


    @Override
    public Class getType() {
        return ApplicationCloudJSONDataEvent.class;
    }

    @Override
    public String getSource() {
        return "#";
    }

    @Override
    public void handle(CloudEvent cloudEvent) {
        log.info("cloudEvent {}",cloudEvent);
    }
}
