package newworldorder.client;

import newworldorder.client.service.GdxAppController;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApplication {

	public static void main(String[] args) {
		@SuppressWarnings("resource")	// Shutdown hook will close the context
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ClientConfiguration.class);
		ctx.refresh();
		ctx.registerShutdownHook();
		GdxAppController.showGdxApp();
	}
}
