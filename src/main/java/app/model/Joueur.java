

package app.model;

import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIdentityReference(alwaysAsId = true)
public class Joueur {
	private int id;
	
	private String nom;
	private String prenom;
	private String avatar;
	private boolean isUnknown;
	private Integer score;
	
	public Joueur(String nom, String prenom, String avatar) {
		super();
		
		this.nom = nom;
		this.prenom = prenom;
		this.avatar = avatar; // default
	}
	
	
	public Joueur(String nom, String prenom) {
		this(nom, prenom, "default");
		
		this.id = -1;
	//	this.score = 0;
		this.isUnknown = true;
	}
	
	public Joueur(int id, String nom, String prenom, int score, String avatar) {
		this(nom, prenom, avatar);
		
		this.id = id;
		this.score = score;
		this.isUnknown = false;
	}
	
	
	public Joueur(int id) {
		this.id = id;
	}
	
	public String getNomComplet() {
		return nom +" "+prenom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public void incrementerScore() {
		this.score++;
	}
	
	public void decrementerScore() {
		this.score--;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}	
	
	public boolean isUnknown() {
		return isUnknown;
	}

	@Override
	public String toString() {
		return "("+nom +" "+ " identifiant: "+id+" Score: "+score+")";
	}
	
	public int ChoisierCoup() {
		System.out.println("Choisir une colonne: ");
		Scanner clavier = new Scanner(System.in);
		int numColonne=clavier.nextInt();
		return numColonne-1;
		/*int numColonne=(int)(Math.random()*7);
		return numColonne;*/		
	}	
}
