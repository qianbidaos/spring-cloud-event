package xyz.qianbidao.springcloudevents.springenventrw.eventhandle;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;

/**
 * @auther 铅笔刀
 * @date 2022/6/5
 */
public class aaa implements ApplicationRunner {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationCloudJSONDataEvent applicationCloudJSONDataEvent = new ApplicationCloudJSONDataEvent("21231", JSON.toJSONString(this));
        applicationEventPublisher.publishEvent(applicationCloudJSONDataEvent);
    }
}
