package app.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.model.Coup;
import app.model.Partie;
import app.model.Position;

public class CoupDAO implements DAO<Coup> {

	@Override
	public Optional<Coup> find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Coup> findAll(Partie partie) {
    	ArrayList<Coup> coups = new ArrayList<>();
		Statement ps;
		ResultSet rs;
		
		try {
			String requete = "select * from coups where partie_id = "+partie.getId();
			ps = MySQL.getConnection().createStatement();
			rs = ps.executeQuery(requete);
			
			while (rs.next()) {
				coups.add(new Coup(rs.getInt("id"), partie, new Position(rs.getInt("px"), rs.getInt("py"))));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coups;
	}
	
	@Override
	public List<Coup> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Coup coup) {
		try {
            Position pos = coup.getPosition();
			Statement statement = MySQL.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO coups(partie_id, id, px, py) VALUES ("+coup.getPartie().getId()+","+coup.getCounter()+","+pos.getPosX()+","+pos.getPosY()+")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Coup t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Coup t) {
		// TODO Auto-generated method stub
		
	}

}

