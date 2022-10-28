package app.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import app.model.Joueur;

public class JoueurDAO implements DAO<Joueur> {

    public ArrayList<Joueur> findAll() {
    	ArrayList<Joueur> joueurs = new ArrayList<>();

		try {
			String requete = "select * from joueurs";
			Statement ps = MySQL.getConnection().createStatement();
			ResultSet rs = ps.executeQuery(requete);
			
			while (rs.next()) {
				joueurs.add(new Joueur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("score"), rs.getString("avatar_path")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return joueurs;
	}
    
    
    public void updateScore(Joueur joueur) {
		try {
			
			PreparedStatement pst = MySQL.getConnection().prepareStatement("update joueurs set score = ? where id = ?");
            pst.setInt(1, joueur.getScore());
            pst.setInt(2, joueur.getId());
            pst.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void resetScores() {
		try {
			
			PreparedStatement pst = MySQL.getConnection().prepareStatement("update joueurs set score = 0 where 1");
            pst.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public Optional<Joueur> find(long id) {
    	Joueur joueur = null;
		
		try {
			String requete = "select * from joueurs where id = "+id;
			Statement ps = MySQL.getConnection().createStatement();
			ResultSet rs = ps.executeQuery(requete);
			
			while (rs.next()) {
				joueur = new Joueur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("score"), rs.getString("avatar_path"));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(joueur);
	}


	@Override
	public void create(Joueur t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Joueur t, String[] params) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Joueur t) {
		// TODO Auto-generated method stub
		
	}
}
