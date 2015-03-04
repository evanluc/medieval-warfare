package newworldorder.server.it;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import newworldorder.common.network.AmqpAdapter;

@Configuration
@PropertySource("classpath:/application.properties")
public class TestConfig {

	@Value("${rabbitmq.host}")
	private String host;
	@Value("${rabbitmq.port}")
	private int port;
	@Value("${rabbitmq.username}")
	private String username;
	@Value("${rabbitmq.password}")
	private String password;
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setAutoStartup(false);
		return container;
	}
	
	@Bean
	AmqpAdapter amqpAdapter(AmqpTemplate template) {
		return new AmqpAdapter(template);
	}
	
	@Bean
	AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
		return new RabbitTemplate(factory);
	}

	@Bean
	AmqpAdmin admin(ConnectionFactory factory) {
		return new RabbitAdmin(factory);
	}
	
	@Bean
	Queue testQueue() {
		return new Queue("test-queue-1");
	}
	
	@Bean
	DirectExchange defaultExchange() {
		return new DirectExchange("");
	}
	
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange("test-direct-exchange");
	}
	
	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange("test-fanout-exchange");
	}
	
	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
