package com.tom.sample.paypal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;

@Configuration
public class PaypalConfig 
{

	@Value("${paypal.client-id}")
	private String clientId;
	
	@Value("${paypal.client-secret}")
	private String clientSecret;
	
	@Value("${paypal.mode}")
	private String mode;
	
	@Bean
	APIContext apiContext() 
	{
		return new APIContext(clientId, clientSecret, mode);
	}
	
}
