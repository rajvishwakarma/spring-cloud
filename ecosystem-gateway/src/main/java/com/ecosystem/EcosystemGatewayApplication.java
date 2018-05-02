package com.ecosystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecosystem.config.GatewayFilter;
import com.ecosystem.config.HystrixFallbackConfiguration;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableHystrix
public class EcosystemGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcosystemGatewayApplication.class, args);
	}

	@Bean
	HystrixFallbackConfiguration hystrixFallbackConfiguration(){
		return new HystrixFallbackConfiguration();
	}
	
	@Bean
	GatewayFilter gatewayFilter(){
		return new GatewayFilter();
	}
	
	@Bean
	public BCryptPasswordEncoder loadEncoder() {
		return new BCryptPasswordEncoder();
	}
}
