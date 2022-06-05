package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import io.cloudevents.CloudEvent;

public interface EventHandle<T> {
    public Class getType();
    public void handle(T cloudEvent);
}
