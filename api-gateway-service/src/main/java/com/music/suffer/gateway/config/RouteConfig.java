package com.music.suffer.gateway.config;

import com.music.suffer.gateway.filter.OAuthSecurityFilter;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {
    private final OAuthSecurityFilter securityFilter;
    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    @Bean
    Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    Decoder feignFormDecoder() {
        return new SpringDecoder(messageConverters);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-api", r -> r.path("/api/user/**")
                    .filters(f -> f.rewritePath("/api/user/(?<segment>.*)", "/${segment}"))
                    .uri("lb://user-service"))
                .route("library-service", r -> r.path("/api/library/**")
                    .filters(f -> f.rewritePath("/api/library/(?<segment>.*)", "/${segment}"))
                    .uri("lb://library-service")
                    .filter(securityFilter))
                .build();
    }
}
