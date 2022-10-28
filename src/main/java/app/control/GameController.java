package app.control;

import java.util.ArrayList;

import app.database.JoueurDAO;
import app.database.PartieDAO;
import app.model.Coup;
import app.model.Joueur;
import app.model.Partie;
import app.model.Position;
import app.model.Puissance;
import app.vue.InterfaceJeuPuissance;
import app.vue.InterfaceJoueur;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class GameController implements Controller {
	private static JoueurDAO joueurDAO;
	private static PartieDAO partieDAO;
	
	BorderPane fenetre = new BorderPane();
	Puissance puissance;
	
	Button[][] tabButton; // Events
	InterfaceJeuPuissance interfaceJeuPuissance;
	InterfaceJoueur interfaceJoueurP1;
	InterfaceJoueur interfaceJoueurP2;
	

	public void onNewInstance() {
		joueurDAO = new JoueurDAO();
		partieDAO = new PartieDAO();
	}
	

	public void initialise() {
		ArrayList<Joueur> players = joueurDAO.findAll();
		puissance = new Puissance(players.get(0), players.get(1));
		
		joueurControl();
		mainControl();
	}
	

	public void mainControl() {
		puissance.start();
		
		// partie vue (Creation de Grid)
		interfaceJeuPuissance = new InterfaceJeuPuissance();
		interfaceJeuPuissance.dessiner();
		
		//gestion d'actions (Button Event)
		tabButton = interfaceJeuPuissance.getTabButton();
		
		Partie partie = puissance.getPartie();
		int nbLigne = InterfaceJeuPuissance.nbLigne, nbColonne = InterfaceJeuPuissance.nbColonne;
		
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbColonne; j++) {
				final int jj = j; //ii = i
				
				tabButton[i][j].setOnAction(event -> {
					gestionAction(partie, jj);
				});
			}
		}
		
		fenetre.setCenter(interfaceJeuPuissance.getRoot());
	}
	
	public void joueurControl() {
		interfaceJoueurP1 = new InterfaceJoueur(puissance.getJ1());
		interfaceJoueurP2 = new InterfaceJoueur(puissance.getJ2());
		
		interfaceJoueurP1.dessiner(10, 20, false);
		interfaceJoueurP2.dessiner(20, 10, false);
		
		fenetre.setLeft(interfaceJoueurP1.getRoot());
		fenetre.setRight(interfaceJoueurP2.getRoot());
	}
	
	private void gestionAction(Partie partie, int jj) {
		
		if (!puissance.estPartieFin()) {
			//model
			int numLigne = puissance.getLigneVideByColonne(jj);
			if(numLigne == -1) {			
				MainController.errorMessage("colonne est déja remplie.");
			} else {			
				Joueur joueur = puissance.Play();
				Coup coup = new Coup(new Position(numLigne, jj));
				partie.getCoups().add(coup);
				
				if (joueur == puissance.getJ1()) {
					interfaceJeuPuissance.colorButton(numLigne, jj, 1);
				} else {
					interfaceJeuPuissance.colorButton(numLigne, jj, 2);
				}
				
				if (puissance.setCoup(joueur.getId(), coup.getPosition())) {
					puissance.Finaliser("Le joueur " + joueur.getNomComplet() + " est gagnant!!", true);
					joueurControl();
				} else if (puissance.verifierGridRemplie()) {
					puissance.Finaliser("La grille est remplie et la partie est NULL!", false);
					
				}
				
			}
		} else {
			MainController.errorMessage("La partie est déjà finie.");
		}
		//view	
	}
	
	
	public Puissance getPuissance() {
		return puissance;
	}
	
	@Override
	public BorderPane getFenetre() {
		return fenetre;
	}

	public static void save(Partie partie) {
		if (partie.getId() == null) {
			System.out.println("Saving players' scores...");
			joueurDAO.updateScore(partie.getJPrimary());
			joueurDAO.updateScore(partie.getJSecondary());
			
			System.out.println("Saving partie in the database...");
			partieDAO.create(partie);
			
			if (partie.getId() != null) {
				FichierController.exporter(partie);
			}
			
		} else {
			MainController.errorMessage("Cette partie est deja sauvagée dans la base de données (ID = "+partie.getId()+").");
		}
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
}
