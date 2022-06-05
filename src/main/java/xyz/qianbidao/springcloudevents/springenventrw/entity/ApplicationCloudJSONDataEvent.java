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
    private String jsonData = new String();

    public ApplicationCloudJSONDataEvent(Object source, String bytes) {
        super(source==null?ApplicationCloudJSONDataEvent.class:source);
        this.jsonData=bytes;
    }

    public ApplicationCloudJSONDataEvent(Object source) {
        super(source);
    }

    public void setData(String data) {
        if(null!=data){
            this.jsonData = data;
        }
    }

    public String getData() {
        return jsonData;
    }
}
