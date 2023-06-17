package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {

	//stato sist.-mondo
	private Map<Team, Integer>mappaTeamNReporter;
	//param.Ingresso
	Model model = new Model();
	private Graph<Team,DefaultWeightedEdge>grafo;
	private Integer n;//num di reporter inizialmente assegnati ad ogni squadra
	private Integer x;//num minimo di reporter che dovrebbero esserci per ogni partita
	private List<Match>allMatches;
	private List<Integer>reporterPerMatch;
	
	//Indicatori uscita
	private Double nReporterInMediaPerPartita;
	private Integer nPartiteSottoLaSogliaDiReporter;
	//coda ed eventi
	private PriorityQueue<Evento>queue;
	
	public Simulatore(Integer n, Integer x) {
		super();
		this.n = n;
		this.x = x;
		this.mappaTeamNReporter = new HashMap<>();
		this.reporterPerMatch = new ArrayList<>();
	}
	
	public void initialize() {
		this.queue = new PriorityQueue<Evento>();
		this.nReporterInMediaPerPartita = 0.0;
		this.nPartiteSottoLaSogliaDiReporter = 0;
		this.allMatches = new ArrayList<>(model.getAllMatches());
		model.creaGrafo();
		this.grafo = model.getGrafo();
		
		for(Team x : grafo.vertexSet()) {
			this.mappaTeamNReporter.put(x, n);
		}
		for(Match z : allMatches) {//aggiungo tutte le partite lette dal DB
			this.queue.add(new Evento(z.getDate().toLocalDate(), EventType.MATCH, model.getIdMapTeams().get(z.getTeamHomeID()),
					model.getIdMapTeams().get(z.getTeamAwayID()), z.getResultOfTeamHome()));
		}
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()){
			Evento e = this.queue.poll();
			
			EventType type = e.getType();
			LocalDate data = e.getData();
			Team casa = e.getSquadraCasa();
			Team ospite = e.getSquadraOspite();
			Integer risultato = e.getRisultato();
			
			System.out.println(e.toString());
			
			switch(type) {
			case MATCH:
				//devo vedere per ogni partita: il numero di reporter e il numero di volte che una partita non ne ha almeno x
				
				int nReporter = this.mappaTeamNReporter.get(casa)+this.mappaTeamNReporter.get(ospite);
				this.reporterPerMatch.add(nReporter);
				if(nReporter < this.x) {
					this.nPartiteSottoLaSogliaDiReporter++;
				}	
				
				if(risultato == 1) {//squadra casa vincente
					if(Math.random() <= 0.5) {
						//tolgo dalla squadra ospite(perdente) un reporter e lo metto in una piu blasonata
						Team migliore = this.getSquadraPiuBlasonata(ospite);
						if(migliore != null) {
							int nR = (this.mappaTeamNReporter.get(ospite)-1);
							this.mappaTeamNReporter.put(ospite, nR);
							int NR = (this.mappaTeamNReporter.get(migliore)+1);
							this.mappaTeamNReporter.put(ospite, NR);
						}
					}
				}
				else if(risultato == -1) {//squadra casa perdente
					if(Math.random() <= 0.2) {
						//tolgo dalla squadra casa(perdente) un reporter e lo metto in una meno blasonata
						Team peggiore = this.getSquadraMenoBlasonata(casa);
						if(peggiore != null) {
							int nR = (this.mappaTeamNReporter.get(casa))-1;
							this.mappaTeamNReporter.put(ospite, nR);
							int NR = (this.mappaTeamNReporter.get(peggiore))+1;
							this.mappaTeamNReporter.put(ospite, NR);
						}
					}
				}
				break;
			}
		}
	}
	
	public Team getSquadraPiuBlasonata(Team sconfitto) {
		List<Team>teamMigliori = new ArrayList<>(model.getSquadreNonBattute(sconfitto));
		Team squadraScelta = null;
		if(!teamMigliori.isEmpty()) {
			int n = (int) (Math.random()*teamMigliori.size());
			squadraScelta = teamMigliori.get(n);
		}
		return squadraScelta;
	}
	
	public Team getSquadraMenoBlasonata(Team sconfitto) {
		List<Team>teamPeggiori = new ArrayList<>(model.getSquadreBattute(sconfitto));
		Team squadraScelta = null;
		if(!teamPeggiori.isEmpty()) {
			int n = (int) (Math.random()*teamPeggiori.size());
			squadraScelta = teamPeggiori.get(n);
		}
		return squadraScelta;
	}
	
	public SimResult risultato() {
		Double somma = 0.0;
		for(Integer x : this.reporterPerMatch) {
			somma += x;
		}
		this.nReporterInMediaPerPartita = somma/this.reporterPerMatch.size();
		SimResult res = new SimResult(this.nReporterInMediaPerPartita, this.nPartiteSottoLaSogliaDiReporter);
		return res;
	}
	
}
