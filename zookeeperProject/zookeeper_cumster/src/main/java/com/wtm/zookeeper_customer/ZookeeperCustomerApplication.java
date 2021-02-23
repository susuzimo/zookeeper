package com.wtm.zookeeper_customer;

import com.wtm.zookeeper_customer.listener.InitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZookeeperCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperCustomerApplication.class, args);
	}



	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean(){
		ServletListenerRegistrationBean servletListenerRegistrationBean=new ServletListenerRegistrationBean();
		servletListenerRegistrationBean.setListener(new InitListener());
		return servletListenerRegistrationBean;
	}
}
