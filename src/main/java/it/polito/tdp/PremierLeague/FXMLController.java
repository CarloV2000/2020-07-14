/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.SimResult;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	Team t = this.cmbSquadra.getValue();
    	if(t == null) {
    		this.txtResult.setText("Inserire una squadra nella boxTeam!");
    		return;
    	}
    	List<Team>squadreBattute = new ArrayList<>(model.getSquadreBattute(t));
    	List<Team>squadreNonBattute = new ArrayList<>(model.getSquadreNonBattute(t));
    	String s = "Squadre con cui "+t.getName()+" ("+t.getPuntiFineAnno()+") ha perso :\n";
    	for(Team x : squadreNonBattute) {
    		s += x.getName()+" ("+(x.getPuntiFineAnno()-t.getPuntiFineAnno())+")\n";
    	}
    	s += "Squadre con cui "+t.getName()+" ha vinto :\n";
    	for(Team x : squadreBattute) {
    		s += x.getName()+" ("+(t.getPuntiFineAnno()-x.getPuntiFineAnno())+")\n";
    	}
    	this.txtResult.setText(s);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String s = model.creaGrafo();
    	this.txtResult.setText(s);
    	
    	for(Team x : model.getAllTeams()) {
    		this.cmbSquadra.getItems().add(x);
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String n = this.txtN.getText();
    	String x = this.txtX.getText();
    	Integer xNUM;
    	Integer nNUM;
    	if(n == "") {
    		this.txtResult.setText("Campo n vuoto");
    		return;
    	}
    	if(x == "") {
    		this.txtResult.setText("Campo x vuoto");
    		return;
    	}
    	
    	try {
    		xNUM = Integer.parseInt(x);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero nel campo x");
    		return;
    	}
    	
    	try {
    		nNUM = Integer.parseInt(n);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero nel campo x");
    		return;
    	}
    	SimResult s = model.simula(nNUM, xNUM);
    	this.txtResult.setText("Simulazione eseguita correttamente:\nmediaPerPartita = "+s.getMediaRepPerMatch()+", nGiorniCritici = "+s.getnGiorniCritici());
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
