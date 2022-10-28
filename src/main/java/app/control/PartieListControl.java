package app.control;

import java.util.ArrayList;

import app.database.CoupDAO;
import app.database.PartieDAO;
import app.model.Partie;
import app.vue.InterfaceCoupsList;
import app.vue.InterfacePartiesList;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;

public class PartieListControl implements Controller {
	private static PartieDAO partieDAO;
	private static CoupDAO coupDAO;
	
	VBox fenetre;
	InterfacePartiesList interfacePartiesList;
	InterfaceCoupsList interfaceCoupsList;
	
	@Override
	public void onNewInstance() {
		partieDAO = new PartieDAO();
		coupDAO = new CoupDAO();
	}
	
	@Override
	public void initialise() {
		mainControl();
	}
	
	@Override
	public void mainControl() {
		interfacePartiesList = new InterfacePartiesList("Liste de parties: ", partieDAO.findAll());
		interfacePartiesList.dessiner();
		
		interfacePartiesList.getTableView().setRowFactory(tv -> {
            TableRow<Partie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	coupControl(row.getItem());
                }
            });
            return row ;
        });
        
		fenetre = interfacePartiesList.getRoot();
	}
	
	public void coupControl(Partie partie) {
		partie.setCoups(coupDAO.findAll(partie));
		
		interfaceCoupsList = new InterfaceCoupsList(partie);
		interfaceCoupsList.dessiner();
        
		fenetre.getChildren().set(0, interfaceCoupsList.getLTitle());
		fenetre.getChildren().set(1, interfaceCoupsList.getTableView());
	}
	
	public VBox getFenetre() {
		return fenetre;
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}
}
