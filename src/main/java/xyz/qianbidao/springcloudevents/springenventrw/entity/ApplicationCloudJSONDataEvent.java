package xyz.qianbidao.springcloudevents.springenventrw.entity;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 * 对外传输的事件
 */
@Getter
public class ApplicationCloudJSONDataEvent extends ApplicationEvent {
    private byte[] data;
    public ApplicationCloudJSONDataEvent(Object source, byte[] bytes) {
        super(source);
        this.data=bytes;
    }

    public ApplicationCloudJSONDataEvent(Object source) {
        super(source);
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
