package app.vue;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class InterfaceMenu {
	MenuBar menu = new MenuBar();
	
	public MenuItem itmSave = new MenuItem("Save");
	public MenuItem itmQuitter = new MenuItem("Quitter");
	
	public MenuItem itmOpen = new MenuItem("Open");
	public MenuItem itmRecommencer = new MenuItem("Recommencer");
	public MenuItem itmContreOrdinateur = new MenuItem("Joueur contre Ordinateur");
	
	public MenuItem itmListePartie = new MenuItem("Liste de parties");
	public MenuItem itmSimulationPartie = new MenuItem("Simuler un partie");
	public MenuItem itmImporterPartie = new MenuItem("Importer un partie");
	public MenuItem itmExporterPartie = new MenuItem("Exporter un partie");
	
	public MenuItem itmListeJoueurs = new MenuItem("Liste de joueurs");
	public MenuItem itmRankingBoard = new MenuItem("Ranking Board");

	
	public InterfaceMenu() {
		dessiner();
	}
	
	public void dessiner() {
		Menu mnuFile = new Menu("File");
		mnuFile.getItems().addAll(itmSave, itmQuitter);
		
		Menu mnuJeu = new Menu("Jeu");
		mnuJeu.getItems().addAll(itmOpen, itmRecommencer, itmContreOrdinateur);
		
		Menu mnuPartie = new Menu("Partie");
		mnuPartie.getItems().addAll(itmListePartie, itmSimulationPartie, itmImporterPartie, itmExporterPartie);
		
		Menu mnuProfil = new Menu("Profil");
		
		Menu mnuStatistique = new Menu("Statistique");
		mnuStatistique.getItems().addAll(itmListeJoueurs, itmRankingBoard);
		
		Menu mnuHelp = new Menu("Help");
		
		menu.getMenus().addAll(mnuFile, mnuJeu, mnuPartie, mnuProfil, mnuStatistique, mnuHelp);
	}
	
	public MenuBar getMenu() {
		return menu;
	}
}
