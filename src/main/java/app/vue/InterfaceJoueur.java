package app.vue;

import app.model.Joueur;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InterfaceJoueur {
	private VBox root = new VBox(6);
	private Joueur joueur;
	
	public InterfaceJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	public void dessiner(int inset_left, int inset_right, boolean small) {
		root.setPadding(new Insets(10, inset_right, 5, inset_left));
		ImageView imageView = new ImageView("/imgs/"+ joueur.getAvatar() + ".png");
		
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		
        BorderPane imgPane = new BorderPane();
        imgPane.setCenter(imageView);
		root.getChildren().add(imgPane);
		
		root.getChildren().add(new Label("Informations:"));
		
		
		//root.getChildren().add(new Label(""));
		
		TextFlow flowNom = new TextFlow();
		Text valueNom = new Text(joueur.getNom());
		valueNom.setStyle("-fx-font-style: italic");
		
		TextFlow flowPrenom = new TextFlow();
		Text valuePrenom = new Text(joueur.getPrenom());
		valuePrenom.setStyle("-fx-font-style: italic");
		

		
		flowNom.getChildren().addAll(new Text("- Nom: "), valueNom);
		flowPrenom.getChildren().addAll(new Text("- Prenom: "), valuePrenom);

		root.getChildren().addAll(flowNom, flowPrenom);
		
		if (small == false) {
			Label scoreLabel = new Label("- Score: " + joueur.getScore());
			root.getChildren().add(scoreLabel);
		}
	}
	
	
	public VBox getRoot() {
	
		return root;
	}
}
