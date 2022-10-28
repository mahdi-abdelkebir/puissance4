package app.control;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import app.AppPuissance;
import app.database.CoupDAO;
import app.database.PartieDAO;
import app.model.Coup;
import app.model.Joueur;
import app.model.Partie;
import app.vue.InterfaceJeuPuissance;
import app.vue.InterfaceJoueur;
import app.vue.InterfacePartiesList;
import app.vue.InterfaceSimulationBar;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulationController implements Controller {
	private static PartieDAO partieDAO;
	private static CoupDAO coupDAO;
	
	BorderPane fenetre; 
	
	Partie partie;
	ArrayList<Coup> coups;
	
	Integer counter, maxCounter;
	Timer timer;
	TimerTask timerTask;
	Boolean stoppedTimer = false;
	
	InterfacePartiesList interfacePartiesList;
	
	InterfaceJeuPuissance interfaceJeuPuissance;
	InterfaceJoueur interfaceJoueurP1;
	InterfaceJoueur interfaceJoueurP2;
	InterfaceSimulationBar interfaceSimulationBar;
	
	@Override
	public void onNewInstance() {
		coupDAO = new CoupDAO();
		partieDAO = new PartieDAO();
	}
	
	@Override
	public void initialise() {
		mainControl();
	}

	@Override
	public void mainControl() {
		fenetre = new BorderPane();
		
		Stage stage = AppPuissance.getStage();
		
		if (stage.getHeight() < 370) {
			stage.setHeight(370);
		}
		
		interfacePartiesList = new InterfacePartiesList("Selectionner un partie pour simuler: ", partieDAO.findAll());
		interfacePartiesList.dessiner();
		
		interfacePartiesList.getTableView().setRowFactory(tv -> {
            TableRow<Partie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	// start
                	simuler(row.getItem());
                }
            });
            return row ;
        });
        
		fenetre.setCenter(interfacePartiesList.getRoot());
	}
	
	
	public void simuler(Partie partie) {
		coups = coupDAO.findAll(partie);
		partie.setCoups(coups);	
		this.partie = partie;
		
		interfaceJeuPuissance = new InterfaceJeuPuissance();
		interfaceJeuPuissance.dessiner();
		
		interfaceJoueurP1 = new InterfaceJoueur(partie.getJPrimary());
		interfaceJoueurP2 = new InterfaceJoueur(partie.getJSecondary());
		interfaceJoueurP1.dessiner(10, 20, true);
		interfaceJoueurP2.dessiner(20, 10, true);
		
		gestionCenter();
		fenetre.setLeft(interfaceJoueurP1.getRoot());
		fenetre.setRight(interfaceJoueurP2.getRoot());
		
		counter = -1;
		maxCounter = coups.size()-1;
		startTimer();
	}
	
	
	private void startTimer() {
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (counter == maxCounter) { // if last
					
					Platform.runLater(()-> {
						
						Alert mBox = new Alert(AlertType.INFORMATION);
						
						if (partie.getScoreP() == partie.getScoreS()) {
							mBox.setContentText("La grille est remplie et la partie est NULL!");
						} else {
							Joueur joueur;
							if (partie.getScoreP() == 1) {
								joueur = partie.getJPrimary();
							} else {
								joueur = partie.getJSecondary();
							}
							
							mBox.setContentText("Le joueur " + joueur.getNom() + " " + joueur.getPrenom() + " est gagnant!!");
						}
						
						mBox.show();

						stopTimer();
						interfaceSimulationBar.btnBack.setDisable(false);
					});
					
				} else {
					increaseCounter();
				}
			}
		};
		
		timer.schedule(timerTask, 0, 700); //wait 0 ms before doing the action and do it evry 1000ms (1second)
		stoppedTimer = false;
	}
	
	private void stopTimer() {
		timer.cancel();
		timerTask.cancel();
		
		stoppedTimer = true;
		interfaceSimulationBar.btnStopContinue.setText("Continue");
	}

	public void gestionCenter() {
		VBox Vbox = new VBox();
		
		interfaceSimulationBar = new InterfaceSimulationBar();
		
		Vbox.getChildren().add(interfaceSimulationBar.getBar());
		Vbox.getChildren().add(interfaceJeuPuissance.getRoot());
		
		interfaceSimulationBar.btnStopContinue.setOnAction(event -> {
			if (stoppedTimer) { // continue
				interfaceSimulationBar.btnStopContinue.setText("Stop");
				startTimer();
				
				if (counter >= 0) {
					interfaceSimulationBar.btnBack.setDisable(true);
					if (counter < maxCounter) {
						interfaceSimulationBar.btnForward.setDisable(true);
					}
				}
					
			} else { // stop
				stopTimer();
				
				interfaceSimulationBar.btnForward.setDisable(false);
				if (counter >= 0) {
					interfaceSimulationBar.btnBack.setDisable(false);
				}
			}
		});
		
		interfaceSimulationBar.btnBack.setOnAction(event -> {
			decreaseCounter();
		});
		
		interfaceSimulationBar.btnForward.setOnAction(event -> {
			increaseCounter();
		});
		
		fenetre.setCenter(Vbox);
	}
	
	public void increaseCounter() {
		Coup currentCoup = coups.get(++counter);
		interfaceJeuPuissance.colorButton(currentCoup.getPosition().getPosX(), currentCoup.getPosition().getPosY(), (counter%2)+1);
		
		if (counter == maxCounter) {
			interfaceSimulationBar.btnStopContinue.setDisable(true);	
			if (stoppedTimer) {
				interfaceSimulationBar.btnForward.setDisable(true);
			}
		} else if (stoppedTimer) { // counter >= 0 //default
			interfaceSimulationBar.btnBack.setDisable(false);
		}
	}
	
	public void decreaseCounter() {
		Coup currentCoup = coups.get(counter--);
		interfaceJeuPuissance.resetColor(currentCoup.getPosition().getPosX(), currentCoup.getPosition().getPosY());
		
		if (counter == -1) {
			interfaceSimulationBar.btnBack.setDisable(true);
			
		} else if (counter+1 == maxCounter) {
			
			interfaceSimulationBar.btnStopContinue.setDisable(false);
			interfaceSimulationBar.btnForward.setDisable(false);
		}
	}

	
	public void onExit() {
		if (this.partie != null) {
			timer.cancel();
			timerTask.cancel();
			stoppedTimer = true;
		}
	}
	
	@Override
	public BorderPane getFenetre() {
		return fenetre;
	}
}
