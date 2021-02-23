package com.wtm.zookeeper_product;

import com.wtm.zookeeper_product.listener.InitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZookeeperProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperProductApplication.class, args);
	}

	/*
	*ServletListenerRegistrationBean
	* 监听器
	 */
	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean(){
		ServletListenerRegistrationBean servletListenerRegistrationBean=new ServletListenerRegistrationBean();
		servletListenerRegistrationBean.setListener(new InitListener());
		return servletListenerRegistrationBean;
	}

}
