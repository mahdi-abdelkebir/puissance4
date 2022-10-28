package app.vue;

import java.util.ArrayList;

import app.model.Joueur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class InterfaceRanking {
	private VBox root = new VBox();
	ArrayList<Joueur> joueurs;
	
	public InterfaceRanking(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	
	public void dessiner() {
		  Label titleLabel = new Label("Ranking: ");
		 
		  TableView<Joueur> table = new TableView<Joueur>();
		  
		  
	      // Setting up the columns
	      TableColumn<Joueur, Integer> IdCol //
	              = new TableColumn<Joueur, Integer>("ID");

	      TableColumn<Joueur, String> nomCol//
	              = new TableColumn<Joueur, String>("Nom");

	      TableColumn<Joueur, String> prenomCol//
	              = new TableColumn<Joueur, String>("Prenom");

	      TableColumn<Joueur, Integer> scoreCol //
	              = new TableColumn<Joueur, Integer>("Score");
	      
	      
	      IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
	      nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
	      prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	      scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
	      
	      
	      // Setting sorting parameters
	      scoreCol.setSortType(TableColumn.SortType.DESCENDING);
	      IdCol.setSortable(false);
	      nomCol.setSortable(false);
	      prenomCol.setSortable(false);
	      
	      
	      // Display row data
	      ObservableList<Joueur> list = FXCollections.observableArrayList(joueurs);
	      table.setItems(list);
	      table.getColumns().addAll(IdCol, nomCol, prenomCol, scoreCol);	
	      table.getSortOrder().add(scoreCol);
	      
	      
	      // Configuring and adding items to root
	      root.setPadding(new Insets(5));
	      root.getChildren().addAll(titleLabel, table);
	}
	
	
	public VBox getRoot() {
		return root;
	}
}
