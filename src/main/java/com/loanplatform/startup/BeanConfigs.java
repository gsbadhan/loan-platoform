package com.loanplatform.startup;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * Properties loading and common beans
 */

@Configuration
@PropertySource("classpath:application.yml")
public class BeanConfigs {

	@Autowired
	private Environment env;

	@Bean
	@Qualifier("authRestTemplate")
	public RestTemplate googleRestTemplateB() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(Integer.parseInt(env.getProperty("auth.resttemplate.maxtotal")));
		connManager.setDefaultMaxPerRoute(Integer.parseInt(env.getProperty("auth.resttemplate.maxperroute")));
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(
						Integer.parseInt(env.getProperty("auth.resttemplate.connectionrequesttimeout")))
				.setConnectTimeout(Integer.parseInt(env.getProperty("auth.resttemplate.connecttimeout")))
				.setSocketTimeout(Integer.parseInt(env.getProperty("auth.resttemplate.sockettimeout"))).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(HttpClientBuilder.create().setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig).build());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}
}
