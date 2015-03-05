package newworldorder.client.gui;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.client.controller.PanelController;

@SuppressWarnings("serial")
@Component
class BasePanel extends JPanel {
	protected PanelController panelController;
	
	@Autowired
	public void setPanelController(PanelController controller) {
		panelController = controller;
	}
}
