package newworldorder.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import newworldorder.client.gui.*;
import newworldorder.client.guihandler.*;
import newworldorder.client.model.ModelController;
import newworldorder.client.networking.*;

public class ClientApplication {

	private ModelController modelController = ModelController.getInstance();
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")	// Shutdown hook will close the context
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ClientConfiguration.class);
		ctx.refresh();
		ctx.registerShutdownHook();
	}
}
