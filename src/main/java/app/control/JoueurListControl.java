package app.control;

import app.database.JoueurDAO;
import app.vue.InterfaceJoueursList;
import javafx.scene.layout.VBox;

public class JoueurListControl implements Controller {
	private static JoueurDAO joueurDAO;
	
	VBox fenetre;
	InterfaceJoueursList interfaceJoueursList;
	
	@Override
	public void onNewInstance() {
		joueurDAO = new JoueurDAO();
	}
	
	@Override
	public void initialise() {
		mainControl();
	}
	
	@Override
	public void mainControl() {
		interfaceJoueursList = new InterfaceJoueursList(joueurDAO.findAll());
		interfaceJoueursList.dessiner();
		fenetre = interfaceJoueursList.getRoot();
	}
	
	public VBox getFenetre() {
		return fenetre;
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
}
