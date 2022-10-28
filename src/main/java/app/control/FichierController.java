package app.control;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.AppPuissance;
import app.database.CoupDAO;
import app.database.JoueurDAO;
import app.database.PartieDAO;
import app.model.Coup;
import app.model.Joueur;
import app.model.Partie;
import app.model.Position;
import app.vue.InterfacePartiesList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FichierController implements Controller {
	private static PartieDAO partieDAO;
	private static CoupDAO coupDAO;
	
	VBox fenetre;
	InterfacePartiesList interfacePartiesList;
	
	public void onNewInstance() {
		partieDAO = new PartieDAO();
		coupDAO = new CoupDAO();
	}
	

	public void initialise() {
		mainControl();
	}
	
	public void mainControl() {
		interfacePartiesList = new InterfacePartiesList("Selectionner un partie pour exporter: ", partieDAO.findAll());
		interfacePartiesList.dessiner();
		
		interfacePartiesList.getTableView().setRowFactory(tv -> {
            TableRow<Partie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	Partie partie = row.getItem();
                	partie.setCoups(coupDAO.findAll(partie));
                	
                	exporter(row.getItem());
                }
            });
            return row;
        });
        
		fenetre = interfacePartiesList.getRoot();
	}
	
	public static void exporter(Partie partie) {
		System.out.println("creating a new partie file...");
		
		ObjectMapper objectMapper = new ObjectMapper();
		File dir = new File("src/main/resources/parties");
		
		if (!dir.exists()) {
			MainController.errorMessage("The directory named 'parties' does not exist in the resources folder.");
			
		} else {
			try {
				String name = "P"+partie.getId()+dir.listFiles().length+".json";
				File file = new File(dir, name);
				file.createNewFile();
				objectMapper.writeValue(file, partie);
				
				MainController.infoMessage("La partie est sauvagée avec succes. La nom de fichier est '"+name+"'.");
	
			} catch (JsonGenerationException e) {
				MainController.errorMessage("Check console.", e);
				
			} catch (JsonMappingException e) {
				MainController.errorMessage("Check console.", e);
				
			} catch (IOException e) {
				MainController.errorMessage("Could not create file for some reason. Check console.", e);
				System.out.println("Full path: "+dir.getAbsolutePath());
			}
		}
	}
	
	//C:\Users\dune2000\Desktop\travail\programmation java avancee\rendus\puissance5\Puissance 5\Projet\src\main\resources
	public static void importer() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.json"));
		 
		 Stage mainStage = AppPuissance.getStage();
		 
		 File selectedFile = fileChooser.showOpenDialog(mainStage);
		 if (selectedFile != null) {
		    // create object mapper instance
		    ObjectMapper mapper = new ObjectMapper();
		    
			try {
				// convert JSON file to map
				Map<?, ?> map = mapper.readValue(selectedFile, Map.class);
				
				SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date creation_date = originalFormat.parse((String) map.get("creationDate"));  
				
				JoueurDAO joueurDAO = new JoueurDAO();
				Integer j1_id = (int) map.get("jprimary"), j2_id = (int) map.get("jsecondary");
				Optional<Joueur> j1_optional = joueurDAO.find(j1_id), j2_optional = joueurDAO.find(j2_id);
				
				if (j1_optional.isEmpty()) {
					MainController.errorMessage("Joueur ID "+j1_id+" doesn't exist in the database.");
					return;
				} else if (j2_optional.isEmpty()) {
					MainController.errorMessage("Joueur ID "+j2_id+" doesn't exist in the database.");
					return;
				}
				
				Joueur j1 = j1_optional.get(), j2 = j2_optional.get();
				
			    Partie partie = new Partie(j1, j2, (int) map.get("scoreP"), (int) map.get("scoreS"), creation_date);
			    
			    ArrayList<Coup> coups = new ArrayList<Coup>();
			    
			    for (LinkedHashMap<String, ?> entry : (ArrayList<LinkedHashMap<String, ?>>) map.get("coups")) {
			    	LinkedHashMap<String, ?> positionMap = (LinkedHashMap<String, ?>) entry.get("position");
			    	
			    	Position pos = new Position((Integer) positionMap.get("posX"), (Integer) positionMap.get("posY"));
			    	coups.add(new Coup((Integer) entry.get("counter"), partie, pos));
			    }
			    
			    partie.setCoups(coups);
	
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confimer");
				alert.setHeaderText("Importer la partie");
				
			    Integer j1_score = partie.getScoreP(), j2_score = partie.getScoreS();
			    String resultat;
			    if (j1_score != j2_score) {
			    	if (j1_score > j2_score) {
			    		resultat = j1.getNomComplet()+" est gagnant!";
			    	} else {
			    		resultat = j2.getNomComplet()+" est gagnant!";
			    	}
			    } else {
			    	resultat = "La partie est NUL!";
			    }

				alert.setContentText("Creation date: "+partie.getCreationDate()+"\n"
						+ "Joueur 1: "+ j1.getNomComplet()+" ("+j1_score+") \n"
						+ "Joueur 2: "+ j2.getNomComplet()+" ("+j2_score+") \n"
						+ "Nombre de coups: "+ coups.size()+" \n"
						+ "Resultat: "+resultat);
				
			     Optional<ButtonType> option = alert.showAndWait();

			     if (option.get() == ButtonType.OK) {
				   if (partieDAO == null) {
					  partieDAO = new PartieDAO();
				   }
				    
				    partieDAO.create(partie);
				   if (partie.getId() != 0) {
					   MainController.infoMessage("La partie est importé dans la base de données avec succes. (ID: "+partie.getId()+")");
				   } else {
					   MainController.errorMessage("DB: La partie ne peut pas importé dans la base de données.");
				   }
				} else {
					MainController.infoMessage("Cancelled!");
				}
			     
			} catch (JsonParseException e) {
				MainController.errorMessage("Check console.", e);
				
			} catch (JsonMappingException e) {
				MainController.errorMessage("Check console.", e);
				
			} catch (ParseException e) {
				MainController.errorMessage("Date was not parsed correctly.", e);
				
			} catch (IOException e) {
				MainController.errorMessage("The file could not be read.", e);
			}
		 }
	}
	
	public VBox getFenetre() {
		return fenetre;
	}

	public void onExit() {
		// TODO Auto-generated method stub
		
	}
}
