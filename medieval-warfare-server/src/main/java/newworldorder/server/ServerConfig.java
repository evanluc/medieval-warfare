package newworldorder.server;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import newworldorder.common.network.message.CommandExecutor;
import newworldorder.common.network.message.CommandListener;

@Configuration
@PropertySource("classpath:/application.properties")
public class ServerConfig {

	@Value("${rabbitmq.host}")
	String host;

	@Value("${rabbitmq.port}")
	int port;

	@Value("${rabbitmq.username}")
	String username;

	@Value("${rabbitmq.password}")
	String password;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setUsername("newworldorder");
		connectionFactory.setPassword("warfare");
		return connectionFactory;
	}

	@Bean
	MessageListener commandListener(CommandExecutor executor) {
		return new CommandListener(executor);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
