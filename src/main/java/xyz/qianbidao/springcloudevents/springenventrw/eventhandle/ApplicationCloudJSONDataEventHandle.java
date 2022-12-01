package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;

import java.lang.reflect.Type;

@Slf4j
public abstract class ApplicationCloudJSONDataEventHandle implements EventHandle<ApplicationCloudJSONDataEvent>{


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
        handleApplicationCloudJSONData(JSON.parseObject(new String(cloudEvent.getData().toBytes())).toJavaObject((Type) getType()));
    }
    public void handleApplicationCloudJSONData(ApplicationCloudJSONDataEvent cloudEvent) {
        log.info("ApplicationCloudJSONDataEvent {}",JSON.toJSONString(cloudEvent));
    }
}
