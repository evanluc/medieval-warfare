package newworldorder.server;

import java.io.IOException;

import newworldorder.common.network.IRoutingProducer;
import newworldorder.common.network.factory.ActorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ServerConfig {
	@Bean
	public IRoutingProducer gameNotifier() throws IOException {
		return ActorFactory.createRoutingProducer("localhost", "notifyExchange");
	}
}
