package com.springcloud.demo.apigateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HystrixCommandController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/hystrixTimeout")
    public String hystrixTimeout() {
        log.error("api-gateway 触发了熔断路由");
        return "超时触发";
    }

    @HystrixCommand(commandKey="myHystrixCommand")
    public String myHystrixCommand() {
        log.warn("api-gateway 熔断处理");
        return "熔断触发";
    }

}