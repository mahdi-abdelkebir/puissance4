package app.vue;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class InterfaceSimulationBar {
	HBox root = new HBox();
	
	public Button btnBack = new Button("<< Back");
	public Button btnStopContinue = new Button("Stop");
	public Button btnForward = new Button("Forward >> ");
	
	public InterfaceSimulationBar() {
		dessiner();
	}
	
	public void dessiner() {
		btnBack.setDisable(true);
		btnForward.setDisable(true);
		
		btnBack.setPrefWidth(80);
		btnForward.setPrefWidth(100);
		btnStopContinue.setPrefWidth(80);
	
		Region filler1 = new Region(), filler2 = new Region(); 
		HBox.setHgrow(filler1, Priority.ALWAYS);
		HBox.setHgrow(filler2, Priority.ALWAYS);
		
		
		root.setSpacing(20);
		root.setPadding(new Insets(5));
		root.getChildren().addAll(btnBack, filler1, btnStopContinue, filler2, btnForward);
	}
	
	public HBox getBar() {
		return root;
	}
}
