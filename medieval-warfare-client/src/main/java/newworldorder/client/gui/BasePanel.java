package newworldorder.client.gui;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import newworldorder.client.controller.IController;
import newworldorder.client.controller.ISession;
import newworldorder.client.controller.PanelController;

@SuppressWarnings("serial")
@Component
class BasePanel extends JPanel {
	@Autowired protected PanelController panelController;
	@Autowired protected IController controller;
	@Autowired protected ISession session;
}
