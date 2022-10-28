package app.vue;

import java.util.ArrayList;
import app.model.Joueur;
import app.model.Partie;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class InterfacePartiesList {
	private VBox root = new VBox();
	private TableView<Partie> table;
	private ArrayList<Partie> parties;
	private String title;
	
	public InterfacePartiesList(String title, ArrayList<Partie> parties) {
		this.title = title;
		this.parties = parties;
	}
	
	public void dessiner() {
		  Label titleLabel = new Label(title);
		  table = new TableView<Partie>();
		  
	      // Setting up the columns
	      TableColumn<Partie, Integer> idCol = 
	    		  new TableColumn<Partie, Integer>("ID");
	      
	      TableColumn<Partie, String> j1Col = new TableColumn<Partie, String>("Joueur 1");
	      TableColumn<Partie, String> np1Col = new TableColumn<Partie, String>("Nom et prenom");
	      TableColumn<Partie, String> sc1Col = new TableColumn<Partie, String>("Score");
	      j1Col.getColumns().addAll(np1Col, sc1Col);
	      
	      TableColumn<Partie, String> j2Col = new TableColumn<Partie, String>("Joueur 2");
	      TableColumn<Partie, String> np2Col = new TableColumn<Partie, String>("Nom et prenom");
	      TableColumn<Partie, String> sc2Col = new TableColumn<Partie, String>("Score");
	      j2Col.getColumns().addAll(np2Col, sc2Col);
	      
	      TableColumn<Partie, String> resultCol = 
	    		  new TableColumn<Partie, String>("Gagnant");
	      
	      TableColumn<Partie, String> creationCol = 
	    		  new TableColumn<Partie, String>("Date de creation");
	   
	      
	      // Setting up the columns' values.
	      idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
	      
	      np1Col.setCellValueFactory(param -> {
	    	  Joueur j1 = param.getValue().getJPrimary();
	    	  return new SimpleStringProperty(j1.getNom() +" "+ j1.getPrenom());  
	      });
	      sc1Col.setCellValueFactory(new PropertyValueFactory<>("scoreP"));
	      
	      
	      np2Col.setCellValueFactory(param -> {
	    	  Joueur j2 = param.getValue().getJSecondary();
	    	  return new SimpleStringProperty(j2.getNom() +" "+ j2.getPrenom());  
	      });
	      sc2Col.setCellValueFactory(new PropertyValueFactory<>("scoreS"));
	      
	      
	      resultCol.setCellValueFactory(param -> {
    		  Integer j1S = param.getValue().getScoreP();
    		  Integer j2S = param.getValue().getScoreS();
    		  
    		  String resultat;
    		  if (j1S > j2S) {
    			  resultat = "Joueur 1";
    		  } else if (j1S < j2S) {
    			  resultat = "Joueur 2";
    		  } else {
    			  resultat = "NUL";
    		  }
    
		      return new SimpleStringProperty(resultat);    
    	  });
	      
	      creationCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

	      
	      // Display row data
	      ObservableList<Partie> list = FXCollections.observableArrayList(parties);
	      table.setItems(list);
	      table.getColumns().addAll(idCol, j1Col, j2Col, resultCol, creationCol);
	      
	      
	      // Configuring and adding items to root
	      root.setPadding(new Insets(5));
	      root.getChildren().add(titleLabel);
	      root.getChildren().add(table);
	}
	
	
	public TableView<Partie> getTableView() {
		return table;
	}
	
	public VBox getRoot() {
		return root;
	}
}
