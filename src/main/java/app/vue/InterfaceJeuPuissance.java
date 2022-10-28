package app.vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;

public class InterfaceJeuPuissance {
	public static int nbLigne = 6;
	public static int nbColonne = 7;
	
	private static String defaultColorJ1 = "LIMEGREEN";
	private static String defaultColorJ2 = "#ff1e00";
	
	private GridPane root = new GridPane();
    private Button[][]tabButton;
    
	public InterfaceJeuPuissance() {
		tabButton=new Button[nbLigne][nbColonne];
	}

	public void dessiner() {
        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rr = new RowConstraints();
        
        cc.setMinWidth(GridPane.USE_PREF_SIZE);
        rr.setMinHeight(GridPane.USE_PREF_SIZE);
        
		root.setHgap(5); root.setVgap(5);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);
        
        root.setStyle("-fx-background-color: #0055ff; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");
        
		double r=18;
		for (int i = 0, ii = nbLigne; i < nbLigne; i++, ii--) {
	        root.getColumnConstraints().add(cc);
			for (int j = 0; j < nbColonne; j++) {
				root.getRowConstraints().add(rr);
				
				Button button = new Button();
				
				button.setShape(new Circle(r));
				button.setMinSize(2*r, 2*r);
				button.setMaxSize(2*r, 2*r);
				
				tabButton[i][j] = button;
				root.add(tabButton[i][j], j, ii);
			}
		}
		
	}
	
    public Button[][] getTabButton(){
    	return this.tabButton;
    }
    
	public GridPane getRoot() {
		return root;
	}

	public void colorButton(int numLigne, int jj, int turn) {
		String color;
		if (turn == 1) {
			color = defaultColorJ1;
		} else {
			color = defaultColorJ2;
		}
		
		tabButton[numLigne][jj].setStyle("-fx-effect: innershadow( gaussian , rgba(255,255,255,0.4) , 20,0,0,0); -fx-background-color: " + color);
	}
	
	public void resetColor(int numLigne, int jj) {
		tabButton[numLigne][jj].setStyle(null);
	}
}
