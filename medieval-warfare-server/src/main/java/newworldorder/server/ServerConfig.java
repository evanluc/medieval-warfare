package newworldorder.server;

import java.io.IOException;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import newworldorder.common.network.AmqpAdapter;
import newworldorder.common.network.command.CommandHandler;
import newworldorder.server.persistence.FileUserStore;

@Configuration
@PropertySource("classpath:/server.properties")
public class ServerConfig {

	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.port}")
	private int port;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.consumeFrom}")
	private String requestExchange;
	
	@Value("${rabbitmq.publishTo}")
	private String notifyExchange;
	
	@Value("${rabbitmq.routingKey}")
	private String routingKey;
	
	@Value("${persistence.file}")
	private String databaseFile;

	@Autowired
	CommandHandler handler;
	
	@Bean
	FileUserStore store() throws IOException {
		return new FileUserStore(databaseFile);
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	@Bean
	public AmqpAdapter amqpAdapter() {
		return new AmqpAdapter(rabbitTemplate());
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
	Queue genQueue() {
		return amqpAdmin().declareQueue();
	}

	@Bean
	DirectExchange requestExchange() {
		return new DirectExchange(requestExchange);
	}
	
	@Bean
	DirectExchange notifyExchange() {
		return new DirectExchange(notifyExchange);
	}
	
	@Bean
	Binding binding() {
		return BindingBuilder.bind(genQueue()).to(requestExchange()).with(routingKey);
	}

	@Bean
	SimpleMessageListenerContainer container() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueues(genQueue());
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
