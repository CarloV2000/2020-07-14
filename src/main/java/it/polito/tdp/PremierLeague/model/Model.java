package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Team, DefaultWeightedEdge>grafo;
	private List<Team>allTeams;
	private PremierLeagueDAO dao;
	private Map<Integer, Team>idMapTeams;
	
	public Model() {
		this.allTeams = new ArrayList<>();
		this.dao = new PremierLeagueDAO();
		this.idMapTeams = new HashMap<>();
	}
	
	public String creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.allTeams = dao.listAllTeams();
		Graphs.addAllVertices(grafo, this.allTeams);
		
		for(Team x : this.allTeams) {
			int punti = dao.getAllPuntiVittorieInCasa(x)+dao.getAllPuntiVittorieInTrasferta(x)+dao.getAllPuntiPareggiInCasa(x)+dao.getAllPuntiPareggiInTrasferta(x);
			x.setPuntiFineAnno(punti);
			
			this.idMapTeams.put(x.getTeamID(), x);
		}
		
		for(Team x : this.allTeams) {
			for(Team y : this.allTeams) {
				if(!x.equals(y)) {
					int peso = 0;
					if(x.getPuntiFineAnno() > y.getPuntiFineAnno()) {
						peso = x.getPuntiFineAnno() - y.getPuntiFineAnno();
						if(peso!=0) {
							grafo.addEdge(x, y);
							grafo.setEdgeWeight(x,  y, peso);
						}
					}
					else if(x.getPuntiFineAnno() < y.getPuntiFineAnno()) {
						peso = y.getPuntiFineAnno() - x.getPuntiFineAnno();
						if(peso!=0) {
							grafo.addEdge(y, x);
							grafo.setEdgeWeight(y, x, peso);
						}
					}
				}
			}
		}
		return "Grafo creato con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi";
	}

	
	public List<Team> getSquadreBattute(Team a){
		List<Team>battute = new ArrayList<>();
		for(Team x : this.allTeams) {
			if(x.getPuntiFineAnno()<a.getPuntiFineAnno()) {
				battute.add(x);
			}
		}
		Collections.sort(battute);
		return battute;
	}
	
	public List<Team> getSquadreNonBattute(Team a){
		List<Team>nonBattute = new ArrayList<>();
		for(Team x : this.allTeams) {
			if(x.getPuntiFineAnno()>a.getPuntiFineAnno()) {
				nonBattute.add(x);
			}
		}
		Collections.sort(nonBattute);
		return nonBattute;
	}
	
	public List<Match>getAllMatches(){
		return dao.listAllMatches();
	}
	
	public SimResult simula(int n, int x) {
		Simulatore sim = new Simulatore(n, x);
		sim.initialize();
		sim.run();
		return sim.risultato();
	}
	
	public Graph<Team, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Team> getAllTeams() {
		return allTeams;
	}

	public Map<Integer, Team> getIdMapTeams() {
		return idMapTeams;
	}
	
	
	
	
	
	
}
