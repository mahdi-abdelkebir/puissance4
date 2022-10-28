package app.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import app.model.Coup;
import app.model.Joueur;
import app.model.Partie;

public class PartieDAO implements DAO<Partie> {

	private static JoueurDAO joueurDAO;
	private static CoupDAO coupDAO;
	
	public PartieDAO() {
		joueurDAO = new JoueurDAO();
		coupDAO = new CoupDAO();
	}
	
	@Override
    public ArrayList<Partie> findAll() {
    	ArrayList<Partie> parties = new ArrayList<Partie>();
		Statement ps;
		ResultSet rs;
		
		try {
			String requete = "select * from parties";
			ps = MySQL.getConnection().createStatement();
			rs = ps.executeQuery(requete);
			
			while (rs.next()) {
				//Joueur(int id, String nom, String prenom, int score, String avatar)
				Joueur j1 = joueurDAO.find(rs.getInt("jP")).get();
				Joueur j2 = joueurDAO.find(rs.getInt("jS")).get();
				
				Partie partie = new Partie(rs.getInt("id"), j1, j2, rs.getInt("scoreP"), rs.getInt("scoreS"), rs.getTimestamp("creationDate"));
				parties.add(partie);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parties;
	}
    
    @Override
    public void create(Partie partie) {
		try {
			
			PreparedStatement pst = MySQL.getConnection().prepareStatement("INSERT INTO parties(jP,jS,scoreP,scoreS,creationDate) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, partie.getJPrimary().getId());
            pst.setInt(2, partie.getJSecondary().getId());
            pst.setInt(3, partie.getScoreP());
            pst.setInt(4, partie.getScoreS());
            pst.setString(5, partie.getCreationDate());
            
            pst.executeUpdate();
            ResultSet keys = pst.getGeneratedKeys();
            
            if(keys.next()){
                int last_inserted_id = keys.getInt(1);
                partie.setId(last_inserted_id);
                
                int counter = 0;
                for (Coup coup : partie.getCoups()) {
                	coup.setPartie(partie);
                	coup.setCounter(++counter);
                	coupDAO.create(coup);
                }
            }
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public Optional<Partie> find(long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void update(Partie t, String[] params) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Partie t) {
		// TODO Auto-generated method stub
		
	}

}
