

package app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "partie"})
public class Coup {
	private int counter;
	private Position position;
	private Partie partie;
	
	public Coup(Position position) {
		this.position = position;
	}
	
	public Coup(Partie partie, Position position) {
		this(position);
		
		this.partie = partie;
	}
	
	public Coup(int counter, Partie partie, Position position) {
		this(partie, position);
		
		this.counter = counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	public void setPartie(Partie partie) {
		this.partie = partie;
	}
	
	public String toString() {
		return counter+" ("+position.getPosX()+", "+position.getPosY()+")";
	}
}
