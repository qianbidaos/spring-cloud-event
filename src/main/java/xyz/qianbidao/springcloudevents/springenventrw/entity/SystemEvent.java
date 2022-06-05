package xyz.qianbidao.springcloudevents.springenventrw.entity;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @auther 铅笔刀
 * @date 2022/5/22
 * 不对外传输的事件
 */
@Getter
public class SystemEvent extends ApplicationEvent {
    private byte[] data;
    public SystemEvent(Object source, byte[] bytes) {
        super(source);
        this.data=bytes;
    }
}
