package xyz.qianbidao.springcloudevents.springenventrw.sender;


import io.cloudevents.CloudEvent;

public interface CloudEventSender {
    public void send(CloudEvent cloudEvent);
}
