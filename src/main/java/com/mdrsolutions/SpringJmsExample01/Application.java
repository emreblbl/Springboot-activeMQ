package com.mdrsolutions.SpringJmsExample01;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;


@SpringBootApplication
@EnableJms
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		Sender sender = context.getBean(Sender.class);
		sender.sendMessage("order-queue","Hello-message!");
		
	}
	@Bean // because method is annotated with @Bean because it'll be made available to the Spring Context as JmsListenerContainerFactory bean.
	public JmsListenerContainerFactory wareHouseFactory(ConnectionFactory factory,
												 DefaultJmsListenerContainerFactoryConfigurer configurer){
		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
		configurer.configure(containerFactory,factory);
		return containerFactory;
	}
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		return new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
	}
	@Bean
	public JmsTemplate jmsTemplate(){
		return new JmsTemplate(connectionFactory());
	}
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(){
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1-1");
		return factory;
	}


}
