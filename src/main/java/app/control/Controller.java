package app.control;

import javafx.scene.Node;

public interface Controller {
	public void initialise();
	public void mainControl();
	public void onNewInstance();
	public void onExit();
	public Node getFenetre();
}
