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

import lombok.extern.slf4j.Slf4j;

@Configuration
@PropertySource("classpath:application.properties")
@Slf4j
public class BeanConfigs {

	@Autowired
	private Environment env;

	@Bean
	@Qualifier("authRestTemplate")
	public RestTemplate googleRestTemplateB() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(20);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(HttpClientBuilder.create().setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig).build());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}
}
