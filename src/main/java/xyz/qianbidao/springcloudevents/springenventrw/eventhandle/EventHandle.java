package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import io.cloudevents.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.StringUtils;
import xyz.qianbidao.springcloudevents.springenventrw.config.EventsConfig;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;
import xyz.qianbidao.springcloudevents.springenventrw.entity.SpringCloudEventsConstant;

public interface EventHandle<T> {

    public default Class getType(){return ApplicationEvent.class;};
    public default String getKey() {
        if(null==getSource()||"".equals(getSource())){
            return getType().getTypeName();
        }
        return getSource() + SpringCloudEventsConstant.DIVISION + getType().getTypeName();
    };
    public default String getSource() {return "";};


    public void handle(CloudEvent cloudEvent);
}
