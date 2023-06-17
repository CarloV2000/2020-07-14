package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.util.Objects;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		MATCH;
	}

	private LocalDate data;
	private EventType type;
	private Team squadraCasa;
	private Team squadraOspite;
	private int risultato;
	
	
	public Evento(LocalDate data, EventType type, Team squadraCasa, Team squadraOspite, int risultato) {
		super();
		this.data = data;
		this.type = type;
		this.squadraCasa = squadraCasa;
		this.squadraOspite = squadraOspite;
		this.risultato = risultato;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	public Team getSquadraCasa() {
		return squadraCasa;
	}
	public void setSquadraCasa(Team squadraCasa) {
		this.squadraCasa = squadraCasa;
	}
	public Team getSquadraOspite() {
		return squadraOspite;
	}
	public void setSquadraOspite(Team squadraOspite) {
		this.squadraOspite = squadraOspite;
	}
	public int getRisultato() {
		return risultato;
	}
	public void setRisultato(int risultato) {
		this.risultato = risultato;
	}
	@Override
	public int hashCode() {
		return Objects.hash(data);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(data, other.data);
	}
	@Override
	public String toString() {
		return "Giorno " + data + ": " + type + " [" + this.squadraCasa+ ", "+this.squadraOspite +": "+ risultato + "]";
	}
	@Override
	public int compareTo(Evento o) {
		return this.data.compareTo(o.data);
	}
	
	
}
