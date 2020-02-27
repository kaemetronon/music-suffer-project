package com.music.suffer.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class OAuthSecurityFilter implements GatewayFilter {
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        if (authorization == null || authorization.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization.get(0));
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<Boolean> validated = restTemplate
                .exchange("lb:user-service/validate", HttpMethod.GET, entity, Boolean.class);
        if (validated.getBody() == null || !validated.getBody()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
