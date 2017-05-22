package com.pizzeria.resource.service.delivery.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@EnableCaching
public class DeliveryLocationServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(DeliveryLocationServiceApplication.class);
	
	/**
	 * Used to initialize the locale message properties.
	 * 
	 * @return the ReloadableResourceBundleMessageSource
	 */
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
	  ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
	  messageBundle.setBasename("classpath:locale/messages");
	  messageBundle.setDefaultEncoding("UTF-8");
	  return messageBundle;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DeliveryLocationServiceApplication.class, args);
		log.info("Delivery location microservice is now available.");
	}
}
