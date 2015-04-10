package newworldorder.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	@Autowired private AnnotationConfigApplicationContext context;
	@Autowired private SimpleMessageListenerContainer container;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		container.start();
		logger.info("MedievalWarfare server started. Ready for commands...");
		UserProbe probe = context.getBean(UserProbe.class);
		scheduler.scheduleAtFixedRate(probe, 30, 30, TimeUnit.SECONDS);
	}

}
