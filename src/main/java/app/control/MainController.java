package app.control;

import java.util.Hashtable;

import app.AppPuissance;
import app.database.MySQL;
import app.model.Puissance;
import app.vue.InterfaceMenu;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
	VBox fenetre = new VBox(3);
	InterfaceMenu menuBar;
	Hashtable<String, Controller> controlMapper = new Hashtable<String, Controller>();
	Controller mainView;
	

	public void initialise() {
		MySQL.connect();
		
		mainControl();
		open("GameController"); 
	}

	public void mainControl() {
		menuBar = new InterfaceMenu();
		
		menuBar.itmRankingBoard.setOnAction(event -> {
			open("RankingControl");
		});
		
		menuBar.itmOpen.setOnAction(event -> {
			open("GameController");
		});
		
		menuBar.itmSave.setOnAction(event -> {
			GameController gameController = (GameController) controlMapper.get("GameController");
			Puissance puissance = gameController.getPuissance();
			
			if (puissance.estPartieFin()) {
				GameController.save(puissance.getPartie());
			} else {
				errorMessage("Cette partie n'est pas encore fini..");
			}
		});
		
		menuBar.itmListeJoueurs.setOnAction(event -> {
			open("JoueurListControl");
		});
		
		menuBar.itmExporterPartie.setOnAction(event -> {
			open("FichierController");
		});
		
		menuBar.itmImporterPartie.setOnAction(event -> {
			FichierController.importer();
		});
		
		menuBar.itmListePartie.setOnAction(event -> {
			open("PartieListControl");
		});
		
		menuBar.itmSimulationPartie.setOnAction(event -> {
			open("SimulationController");
		});
		
		menuBar.itmQuitter.setOnAction(event -> {
			Platform.exit();
		});
		
		menuBar.itmRecommencer.setOnAction(event -> {
			if (controlMapper.containsKey("GameController")) {
				GameController gameController = (GameController) controlMapper.get("GameController");
				if (mainView != gameController) {
					open("GameController");
				}
				gameController.mainControl();
			} else {
				open("GameController");
			}
		});
		
		fenetre.getChildren().addAll(menuBar.getMenu(), new Label("Placeholder"));
	}
	
	public void open(String control) {
		Controller controller;
		if (controlMapper.containsKey(control)) {
			controller = controlMapper.get(control);
			if (control != "GameController") {
				controller.initialise();
			} else {
				Stage stage = AppPuissance.getStage();
				
				if (stage.getHeight() > 340) {
					stage.setHeight(340);
				}
			}
			changeTo(controller);
			
			
		} else {
			
			try {
				controller = (Controller) Class.forName("app.control."+control).getDeclaredConstructor().newInstance();
				controlMapper.put(control, controller);
				controller.onNewInstance();
				controller.initialise();
				changeTo(controller);
				
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//				
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
				
			} catch (Exception e) {
				errorMessage("Exception occurred. Check console.", e);
			}
		}
		
		
		fenetre.getChildren().set(1, mainView.getFenetre());
	}
	
	private void changeTo(Controller controller) {
		if (mainView != null) {
			mainView.onExit();
		}
		
		mainView = controller;
	}

	public static void infoMessage(String content) {
		Alert mBox = new Alert(AlertType.INFORMATION, content);
		mBox.setResizable(true);
		mBox.show();
	}
	
	public static void errorMessage(String content) {
		Alert mBox = new Alert(AlertType.ERROR, content);
		mBox.setResizable(true);
		mBox.show();
	}
	
	public static void errorMessage(String content, Exception e) {
		errorMessage(e.getClass().getName()+": "+content);
		e.printStackTrace();
	}
	
	
	public VBox getFenetre() {
		return fenetre;
	}
}
