package app.control;

import app.database.JoueurDAO;
import app.vue.InterfaceRanking;
import javafx.scene.layout.VBox;

public class RankingControl implements Controller {
	private static JoueurDAO joueurDAO;
	
	VBox fenetre;
	InterfaceRanking interfaceRanking;
	
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
		interfaceRanking = new InterfaceRanking(joueurDAO.findAll());
		interfaceRanking.dessiner();
		fenetre = interfaceRanking.getRoot();
	}
	
	public VBox getFenetre() {
		return fenetre;
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
}
