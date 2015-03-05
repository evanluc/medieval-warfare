package newworldorder.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import newworldorder.client.controller.PanelController;

public class ClientApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ClientConfiguration.class);
		ctx.refresh();
		PanelController panelController = ctx.getBean(PanelController.class);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				panelController.showMainView();
//				ctx.close();
			}
		});
	}
}
