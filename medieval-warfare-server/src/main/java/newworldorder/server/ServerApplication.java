package newworldorder.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	private AnnotationConfigApplicationContext context;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("MedeivalWarfare server ready for messages...");
		Thread.sleep(50000);
		context.close();
	}

}
