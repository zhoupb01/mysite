package com.zhoupb.mysite.gateway.filter;

import com.zhoupb.mysite.common.exception.CustomException;
import com.zhoupb.mysite.common.util.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorizationFilter implements Ordered, GlobalFilter {

	private static final Set<String> EXCLUDE_PATH_PATTERN = new HashSet<>();

	static
	{
		EXCLUDE_PATH_PATTERN.add("/api/account/v1/authorization/signin");
		EXCLUDE_PATH_PATTERN.add("/api/blog/v1/content/{id}");
		EXCLUDE_PATH_PATTERN.add("/api/blog/v1/list");
	}

	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getPath().toString();
		for ( String pattern : EXCLUDE_PATH_PATTERN )
		{
			// 不用校验
			if ( ANT_PATH_MATCHER.match(pattern, path) )
				return chain.filter(exchange);
		}
		String token = exchange.getRequest().getHeaders().getFirst("Authorization");
		try {
			long accountId = JWTUtil.validateToken(token);
			ServerHttpRequest mutateRequest = exchange.getRequest().mutate().header("accountId", String.valueOf(accountId)).build();
			return chain.filter(exchange.mutate().request(mutateRequest).build());
		} catch (CustomException e) {
			exchange.getResponse().setStatusCode(HttpStatus.valueOf(e.getCode()));
			return exchange.getResponse().setComplete();
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
