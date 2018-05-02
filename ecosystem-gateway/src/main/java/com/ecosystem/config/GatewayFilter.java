package com.ecosystem.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class GatewayFilter extends ZuulFilter{
	
	private static Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
	
	@Value("${ecosystem.auth.key}")
	private String authKey;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		logger.info("Gateway Calling");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if(null == request.getHeader("Authorization") || 
				(null != request.getHeader("Authorization") && !bCryptPasswordEncoder.matches(request.getHeader("Authorization"), authKey))){
			
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("{\"Authentication Failed\": \"Full authentication is required to access this resource!\"}");
		}
		return null;
	}
}
