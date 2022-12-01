package xyz.qianbidao.springcloudevents;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import xyz.qianbidao.springcloudevents.springenventrw.entity.ApplicationCloudJSONDataEvent;

@SpringBootApplication
@EnableRabbit
public class SpringCloudEventsApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringCloudEventsApplication.class, args);
        Thread.sleep(100000);
    }

}
