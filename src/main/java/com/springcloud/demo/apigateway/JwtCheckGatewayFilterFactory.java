package com.springcloud.demo.apigateway;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.tuple.Tuple;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class JwtCheckGatewayFilterFactory implements GatewayFilterFactory<Object> {

    public GatewayFilter apply(Tuple args) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            String openId =  exchange.getRequest().getQueryParams().getFirst("openId");
            //check token
            if (StringUtils.isNotBlank(token)) {
                String tokenOpenID = ""; // JwtUtil.verifyToken(token);
                if(StringUtils.isNotBlank(tokenOpenID)) {
                    if(openId != null) {
                        if(openId.equals(tokenOpenID)) {
                            return chain.filter(exchange);
                        }
                    } else {
                        return chain.filter(exchange);
                    }
                }

            }

            //不合法
            ServerHttpResponse response = exchange.getResponse();
            //设置headers
            HttpHeaders httpHeaders = response.getHeaders();
            httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
            httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            //设置body
            //JsonPackage jsonPackage = new JsonPackage();
            //jsonPackage.setStatus(110);
            //jsonPackage.setMessage("未登录或登录超时");
            //DataBuffer bodyDataBuffer = response.bufferFactory().wrap(jsonPackage.toJSONString().getBytes());

            return null;// response.writeWith(Mono.just(bodyDataBuffer));
        };
    }

    @Override
    public GatewayFilter apply(Consumer<Object> consumer) {
        return null;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return null;
    }
}