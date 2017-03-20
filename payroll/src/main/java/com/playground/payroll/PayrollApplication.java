package com.playground.payroll;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The spring boot application.
 * 
 * @author Justin Taylor
 * @version %I%, %G%
 * @since 1.0
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { PayrollApplication.class, Jsr310JpaConverters.class })
@EnableCaching
public class PayrollApplication extends SpringBootServletInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(PayrollApplication.class);
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PayrollApplication.class);
    }
	
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
	
	/**
	 * This is used to expose the post processor so that bean validation may occur
	 * at the service component and not only at the controller component.
	 * 
	 * @return MethodValidationPostProcessor
	 */
	@Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }
     
	/**
	 * This is used to expose the validator so that MethodValidationPostProcessor may have
	 * have access to the local validator.
	 * 
	 * @return Validator
	 */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    } 
    
	public static void main(String[] args) {
		SpringApplication.run(PayrollApplication.class, args);
		log.info("Payroll is now available.");
	}
}
