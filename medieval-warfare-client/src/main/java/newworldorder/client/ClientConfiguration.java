package newworldorder.client;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import newworldorder.client.controller.IController;
import newworldorder.client.controller.ISession;
import newworldorder.client.service.GdxAppController;
import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.CommandConsumer;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.common.service.IGameLauncher;

@Configuration
@ComponentScan
@PropertySource("classpath:/rabbitmq.properties")
public class ClientConfiguration {

	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.port}")
	private int port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.consumeFrom}")
	private String consumerExchange;
	
	@Value("${rabbitmq.publishTo}")
	private String commandExchange;
	
	@Autowired
	CommandHandler handler;
	
	@Autowired
	IController controller;
	
	@Autowired
	ISession session;
	
	@Bean IGameLauncher gameLauncher() {
		return new GdxAppController();
	}
	
	@Bean
	public AmqpAdapter adapter() {
		return new AmqpAdapter(rabbitTemplate());
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	@Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

	@Bean
	DirectExchange commandExchange() {
		return new DirectExchange(commandExchange);
	}
	
	@Bean
	DirectExchange consumerExchange() {
		return new DirectExchange(consumerExchange);
	}
	
	@Bean
	CommandConsumer consumer() {
		return new CommandConsumer(amqpAdmin(), container());
	}

	@Bean
	SimpleMessageListenerContainer container() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setAutoStartup(false);
		container.setConnectionFactory(connectionFactory());
		container.setMessageListener(listenerAdapter());
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter() {
		return new MessageListenerAdapter(handler, "handle");
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}