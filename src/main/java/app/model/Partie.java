

package app.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "puissance", "id"})

public class Partie {
	private Integer id;
	private Joueur joueurPrimary;
	private Joueur joueurSecondary;
	private Puissance puissance;
	private Integer scoreP;
	private Integer scoreS;
	private Date creationDate;
	
	ArrayList<Coup> coups = new ArrayList<Coup>();
	
	public Partie(Joueur j1, Joueur j2) {
		joueurPrimary = j1;
		joueurSecondary = j2;
	}
	
	public Partie(Puissance puissance) {
		this(puissance.getJ1(), puissance.getJ2());
		
		setCreationDate(new Date());
		this.puissance = puissance;
	}
	
	public Partie(Joueur j1, Joueur j2, int scoreJ1, int scoreJ2, Date creation_date) {
		this(j1, j2);
	
		this.scoreP = scoreJ1;
		this.scoreS = scoreJ2;
		this.setCreationDate(creation_date);
	}
	
	public Partie(int id, Joueur j1, Joueur j2, int scoreJ1, int scoreJ2, Date creation_date) {
		this(j1, j2, scoreJ1, scoreJ2, creation_date);
		
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;;
	}
	
	public void setCoups(ArrayList<Coup> coups) {
		this.coups = coups;
	}
	
	public ArrayList<Coup> getCoups() {
		return this.coups;
	}
	
	public void setJPrimary(Joueur j) {
		this.joueurPrimary = j;
	}
	
	public Joueur getJPrimary() {
		return this.joueurPrimary;
	}
	
	public void setJSecondary(Joueur j) {
		this.joueurPrimary = j;
	}
	
	public Joueur getJSecondary() {
		return this.joueurSecondary;
	}
	
	public void setScores(int j1, int j2) {
		this.scoreP = j1;
		this.scoreS = j2;
	}
	
	public int getScoreP() {
		return this.scoreP;
	}
	
	public int getScoreS() {
		return this.scoreS;
	}

	public String getCreationDate() {
		 String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	     // Create object of SimpleDateFormat and pass the desired date format.
	     SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
	     
		return formatter.format(creationDate) ;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
