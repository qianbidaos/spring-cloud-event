package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import io.cloudevents.CloudEvent;

public interface EventHandle<T> {
    public default Class getType(){return  null;};
    public default String getKey() {return  null;};
    public void handle(T cloudEvent);
}
