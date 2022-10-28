package app.vue;

import app.model.Partie;
import app.model.Coup;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InterfaceCoupsList {
	Label titleLabel;
	private TableView<Coup> table;
	
	Partie partie;
	
	public InterfaceCoupsList(Partie partie) {
		this.partie = partie;
	}
	
	public void dessiner() {
		 titleLabel = new Label("Liste de coups de la partie (ID: "+partie.getId()+"): ");
			
		 table = new TableView<Coup>();
		  
		  // Setting up the columns
		  TableColumn<Coup, Integer> idCol = 
				  new TableColumn<Coup, Integer>("Number");
		
		  TableColumn<Coup, Integer> pxCol = 
				  new TableColumn<Coup, Integer>("Pos X");
		  TableColumn<Coup, Integer> pyCol = 
				  new TableColumn<Coup, Integer>("Pos Y");
		  
		  TableColumn<Coup, String> joueurCol = 
				  new TableColumn<Coup, String>("Joueur");
		  
		  
		  // Setting up the columns' values
		  idCol.setCellValueFactory(new PropertyValueFactory<>("counter"));
		  
		  pxCol.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPosition().getPosX()).asObject());
		  pyCol.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getPosition().getPosY()).asObject());
		  
		  joueurCol.setCellValueFactory(param -> new SimpleStringProperty("Joueur "+(((param.getValue().getCounter()-1)%2)+1)));
		  
		  // Display row data
		  ObservableList<Coup> list = FXCollections.observableArrayList(partie.getCoups());
		  table.setItems(list);
		  table.getColumns().addAll(idCol, pxCol, pyCol, joueurCol);
		  
		  
		  // Configuring and adding items to root
		  table.setPadding(new Insets(5));
	}
	
	
	public Label getLTitle() {
		return titleLabel;
	}
	
	public TableView<Coup> getTableView() {
		return table;
	}

}
