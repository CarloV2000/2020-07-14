package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT DISTINCT* FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.*, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID ";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getAllPuntiVittorieInCasa(Team t){
		String sql = "SELECT COUNT(*)AS puntiV "
				+ "FROM matches m "
				+ "WHERE m.TeamHomeID = ? "
				+ "AND m.ResultOfTeamHome = 1 ";
		int punti = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			ResultSet res = st.executeQuery();
			if (res.first()) {
				punti = 3*res.getInt("puntiV");
			}
			conn.close();
			return punti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getAllPuntiVittorieInTrasferta(Team t){
		String sql = "SELECT COUNT(*)AS puntiV "
				+ "FROM matches m "
				+ "WHERE  m.TeamAwayID = ? "
				+ "AND m.ResultOfTeamHome = -1 ";
		int punti = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			ResultSet res = st.executeQuery();
			if (res.first()) {
				punti = 3*res.getInt("puntiV");
			}
			conn.close();
			return punti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Integer getAllPuntiPareggiInCasa(Team t){
		String sql = "SELECT COUNT(*)AS puntiP "
				+ "FROM matches m "
				+ "WHERE m.TeamHomeID = ? "
				+ "AND m.ResultOfTeamHome = 0 ";
		int punti = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			ResultSet res = st.executeQuery();
			if (res.first()) {
				punti = res.getInt("puntiP");
			}
			conn.close();
			return punti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Integer getAllPuntiPareggiInTrasferta(Team t){
		String sql = "SELECT COUNT(*)AS puntiP "
				+ "FROM matches m "
				+ "WHERE m.TeamAwayID = ? "
				+ "AND m.ResultOfTeamHome = 0 ";
		int punti = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, t.getTeamID());
			ResultSet res = st.executeQuery();
			if (res.first()) {
				punti = res.getInt("puntiP");
			}
			conn.close();
			return punti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
