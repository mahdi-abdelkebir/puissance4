package app.model;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Puissance {
	private boolean fin = false;
	private int nbLigne = 6;
	private int nbColonne = 7;
	private Joueur J1, J2;
	private int turn = 1;
	private int[][] grille = new int[6][7];
	private ArrayList<Partie> parties = new ArrayList<Partie>();
	private Partie currentPartie;
	
	public Puissance(Joueur j1, Joueur j2) {
		this.J1 = j1;
		this.J2 = j2;
	}

	public Puissance(Puissance puissance) {

		for (int j = 0; j < nbColonne; j++)
			for (int i = 0; i < nbLigne; i++)
				grille[i][j] = puissance.getValeurPosition(i, j);
		
		this.J1 = puissance.getJ1();
		this.J2 = puissance.getJ2();
		nbLigne = puissance.nbLigne;
		nbColonne = puissance.nbColonne;
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public int getNbColonne() {
		return nbColonne;
	}

	public int getValeurPosition(int numLigne, int numColonne) {
		return grille[numLigne][numColonne];
	}

	public boolean estPositionVide(int numLigne, int numColonne) {
		if (grille[numLigne][numColonne] == 0)
			return true;
		return false;
	}

	public boolean setCoup(int posL, int posC, int idJoueur) {
		grille[posL][posC] = idJoueur;
		return estGagnant(posL, posC, idJoueur);

	}

	public boolean setCoup(int idJoueur, Position pos) {
		grille[pos.getPosX()][pos.getPosY()] = idJoueur;
		return estGagnant(pos, idJoueur);
	}
	
	public boolean setCoup(Joueur joueur, Coup coup) {
		grille[coup.getPosition().getPosX()][coup.getPosition().getPosY()] = joueur.getId();
		return estGagnant(coup.getPosition(), joueur.getId());
	}

	public boolean estGagnant(int posL, int posC, int idJoueur) {
		boolean fin = false;
		Position pos = new Position(posL, posC);
		fin = alignementH(pos, idJoueur) || alignementV(pos, idJoueur) | alignementD(pos, idJoueur);
		return fin;
	}

	public boolean estGagnant(Position pos, int idJoueur) {
		boolean fin = false;
		fin = alignementH(pos, idJoueur) || alignementV(pos, idJoueur) | alignementD(pos, idJoueur);
		return fin;
	}

	private boolean alignementD(Position pos, int idJoueur) {
		int k = 0;
		int j = pos.getPosY();
		int i = pos.getPosX();
		while (j >= 0 && i >= 0 && grille[i][j] == idJoueur) {
			k++;
			j--;
			i--;
		}
		j = pos.getPosY() + 1;
		i = pos.getPosX() + 1;
		while (j < nbColonne && i < nbLigne && grille[i][j] == idJoueur) {
			k++;
			j++;
			i++;
		}
		if (k >= 4)
			return true;
		////
		k = 0;
		j = pos.getPosY();
		i = pos.getPosX();
		while (j >= 0 && i < nbLigne && grille[i][j] == idJoueur) {
			k++;
			j--;
			i++;
		}
		j = pos.getPosY() + 1;
		i = pos.getPosX() - 1;
		while (j < nbColonne && i >= 0 && grille[i][j] == idJoueur) {
			k++;
			j++;
			i--;
		}
		if (k >= 4)
			return true;
		return false;
	}

	private boolean alignementV(Position pos, int idJoueur) {
		int k = 0;
		int i = pos.getPosX();
		while (i >= 0 && grille[i][pos.getPosY()] == idJoueur) {
			k++;
			i--;
		}
		if (k == 4)
			return true;
		return false;
	}

	private boolean alignementH(Position pos, int idJoueur) {
		int k = 0;
		int j = pos.getPosY();
		while (j >= 0 && grille[pos.getPosX()][j] == idJoueur) {
			k++;
			j--;
		}
		j = pos.getPosY() + 1;
		while (j < 7 && grille[pos.getPosX()][j] == idJoueur) {
			k++;
			j++;
		}
		if (k >= 4)
			return true;
		return false;
	}

	public int getLigneVideByColonne(int numColonne) {
		int numLigne = 0;
		while (numLigne < 6 && !estPositionVide(numLigne, numColonne))
			numLigne++;
		if(numLigne==6) return -1;
		return numLigne;
	}

	public void initialiseGrille() {
		for (int i = 0; i < nbLigne; i++)
			for (int j = 0; j < nbColonne; j++)
				grille[i][j] = 0;
	}

	public String toString() {
		String ch = "";
		for (int i = nbLigne - 1; i >= 0; i--) {
			ch = ch + (i + 1) + " ";
			for (int j = 0; j < nbColonne; j++) {
				String car = "-";
				if (grille[i][j] == J1.getId())
					car = "+";
				else if (grille[i][j] == J2.getId())
					car = "*";
				ch = ch + "| " + car;
			}
			ch = ch + "|\n";
		}
		ch = ch + "  ";
		for (int j = 0; j < nbColonne; j++)
			ch = ch + "| " + (j + 1);
		return ch;
	}

	private boolean alignementVAutre(Position pos, int idJoueur) {
		int k = 0;
		int i = pos.getPosX() - 1;
		while (i >= 0 && (grille[i][pos.getPosY()] != idJoueur && grille[i][pos.getPosY()] != 0)) {
			k++;
			i--;
		}
		if (k == 3)
			return true;
		return false;
	}

	private boolean alignementHAutre(Position pos, int idJoueur) {
		int k = 0;
		int j = pos.getPosY() - 1;
		while (j >= 0 && (grille[pos.getPosX()][j] != idJoueur && grille[pos.getPosX()][j] != 0)) {
			k++;
			j--;
		}
		j = pos.getPosY() + 1;
		while (j < 7 && (grille[pos.getPosX()][j] != idJoueur && grille[pos.getPosX()][j] != 0)) {
			k++;
			j++;
		}
		if (k >= 3)
			return true;
		return false;
	}

	private boolean alignementDAutre(Position pos, int idJoueur) {
		int k = 0;
		int j = pos.getPosY() - 1;
		int i = pos.getPosX() - 1;
		while (j >= 0 && i >= 0 && (grille[i][j] != idJoueur && grille[i][j] != 0)) {
			k++;
			j--;
			i--;
		}
		j = pos.getPosY() + 1;
		i = pos.getPosX() + 1;
		while (j < nbColonne && i < nbLigne && (grille[i][j] != idJoueur && grille[i][j] != 0)) {
			k++;
			j++;
			i++;
		}
		if (k >= 3)
			return true;
		////
		k = 0;
		j = pos.getPosY() - 1;
		i = pos.getPosX() + 1;
		while (j >= 0 && i < nbLigne && (grille[i][j] != idJoueur && grille[i][j] != 0)) {
			k++;
			j--;
			i++;
		}
		j = pos.getPosY() + 1;
		i = pos.getPosX() - 1;
		while (j < nbColonne && i >= 0 && (grille[i][j] != idJoueur && grille[i][j] != 0)) {
			k++;
			j++;
			i--;
		}
		if (k >= 3)
			return true;
		return false;
	}

	public void Finaliser(String message, Boolean win) {
		Alert mBox = new Alert(AlertType.INFORMATION, message);
		mBox.show();
		
		fin = true;
		
		if (win != null) {
			if (win) {
				if (turn == 1) {
					J2.incrementerScore();
					currentPartie.setScores(0, 1);
				} else {
					J1.incrementerScore();
					currentPartie.setScores(1, 0);
				}
			} else {
				currentPartie.setScores(0, 0);
			}
		}
	}

	public boolean estPartieFin() {
		return fin;
	}
	
	public Joueur getJ1() {
		return J1;
	}

	public Joueur getJ2() {
		return J2;
	}

	public Joueur Play() {
		if (++turn == 3) {
			turn = 1;
			return J2;
		}
		return J1;
	}

	public boolean verifierGridRemplie() {
		int vide = 0;
		for (int y = 0; y < nbColonne; y++) {
			if (getLigneVideByColonne(y) == -1) {
				vide++;
			}
		}
		
		return vide == nbColonne;
	}
	
	public void start() {
		this.currentPartie = new Partie(this);
		
		this.fin = false;
		this.turn = 1;
		this.grille = new int[6][7];
		
		parties.add(currentPartie);
	}
	
	
	public ArrayList<Partie> getParties() {
		return parties;
	}

	public void setPartie(Partie partie) {
		this.currentPartie = partie;
	}
	
	public Partie getPartie() {
		return this.currentPartie;
	}
}
