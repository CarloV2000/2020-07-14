package it.polito.tdp.PremierLeague.model;

public class SimResult {
	private Double mediaRepPerMatch;
	private Integer nGiorniCritici;
	
	public SimResult(Double mediaRepPerMatch, Integer nGiorniCritici) {
		super();
		this.mediaRepPerMatch = mediaRepPerMatch;
		this.nGiorniCritici = nGiorniCritici;
	}
	
	public Double getMediaRepPerMatch() {
		return mediaRepPerMatch;
	}
	public void setMediaRepPerMatch(Double mediaRepPerMatch) {
		this.mediaRepPerMatch = mediaRepPerMatch;
	}
	public Integer getnGiorniCritici() {
		return nGiorniCritici;
	}
	public void setnGiorniCritici(Integer nGiorniCritici) {
		this.nGiorniCritici = nGiorniCritici;
	}
	
	

}
