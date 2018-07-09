package com.springcloud.demo.apigateway;


import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MyConfiguration {

    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new ElapsedGatewayFilterFactory();
    }

//    @Bean
//    public TokenFilter tokenFilter(){
//        return new TokenFilter();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(ThrottleWebFilterFactory throttle) {
//        return Routes.locator()
//                .route("test")
//                .uri("http://httpbin.org:80")
//                .predicate(host("**.abc.org").and(path("/image/png")))
//                .addResponseHeader("X-TestHeader", "foobar")
//                .and()
//                .route("test2")
//                .uri("http://httpbin.org:80")
//                .predicate(path("/image/webp"))
//                .add(addResponseHeader("X-AnotherHeader", "baz"))
//                .and()
//                .build();
//    }

    //@Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        // @formatter:off
        return builder.routes()
                .route(r -> r.path("/fluent/customer/**")
                        .filters(f -> f.stripPrefix(2)
                                .filter(new ElapsedFilter())
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://CONSUMER")
                        .order(0)
                        .id("fluent_customer_service")
                )
                .build();
        // @formatter:on
    }

}
