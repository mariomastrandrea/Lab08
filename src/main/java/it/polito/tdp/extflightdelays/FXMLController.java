package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.AirportsModel;
import it.polito.tdp.extflightdelays.model.Link;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public class FXMLController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputTextField;

    @FXML
    private Button analizzaAeroportiButton;

    @FXML
    private TextArea resultTextArea;
    
    private AirportsModel model;

    @FXML
    void handleAnalizzaAeroporti(ActionEvent event) 
    {
    	String input = this.inputTextField.getText();
    	int minMiles;
    	
    	try
		{
    		minMiles = Integer.parseInt(input);
		}
		catch(NumberFormatException nfe)
		{
			nfe.printStackTrace();
			this.resultTextArea.setText("Input error: incorrect format");
			return;
		}
    	
    	long nanoStart = System.nanoTime();
    	this.model.createGraph(minMiles); //****
    	long nanoEnd = System.nanoTime();
    	String time = String.format("%.3f", (nanoEnd-nanoStart)/1000000.0);
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Graph created!\nTime: ").append(time).append("ms\n\n");
    	
    	int numVertices = this.model.getNumVertices();
    	int numEdges = this.model.getNumEdges();
    	Collection<Link> links = this.model.getEdges();
    	
    	sb.append("Number of vertices: ").append(numVertices).append("\n");
    	sb.append("Number of edges: ").append(numEdges).append("\n");
    	
    	for(Link l : links)
    	{
    		sb.append("\n").append(l.getA1().toString()).append("   <-->   ");
    		sb.append(l.getA2()).append("     >>   ");
    		sb.append(String.format("%.3f", l.getAvgDistance()));
    		sb.append(" miles");
    	}
    	
    	this.resultTextArea.setText(sb.toString());
    }

    @FXML
    void handleInputType(KeyEvent event) 
    {
    	String input = this.inputTextField.getText();
    	
    	if(input == null || input.isBlank())
    		this.analizzaAeroportiButton.setDisable(true);
    	else
    		this.analizzaAeroportiButton.setDisable(false);
    }

    @FXML
    void initialize() 
    {
        assert inputTextField != null : "fx:id=\"inputTextField\" was not injected: check your FXML file 'Scene_lab08.fxml'.";
        assert analizzaAeroportiButton != null : "fx:id=\"analizzaAeroportiButton\" was not injected: check your FXML file 'Scene_lab08.fxml'.";
        assert resultTextArea != null : "fx:id=\"resultTextArea\" was not injected: check your FXML file 'Scene_lab08.fxml'.";
       
        this.inputTextField.setTextFormatter(new TextFormatter<>(change -> 
        {
        	String text = change.getText();
        	if(text == null || text.isEmpty())
        		return change;
        	
        	int alreadyPresentChars = this.inputTextField.getText().length();
        	int maxNumChars = 6 - alreadyPresentChars;
        	StringBuilder newTextSb = new StringBuilder(text);
        	
        	if(newTextSb.length() > maxNumChars)
        		newTextSb.delete(maxNumChars, newTextSb.length());
        	
        	String newText = newTextSb.toString();
        	
        	if(!newText.matches("[\\d]+"))
        		newText = newText.replaceAll("\\D", "");
        	
        	if(alreadyPresentChars == 0 && newText.matches("(0)+(.)*"))
        		newText = newText.replaceFirst("(0)+", "");
        	
        	change.setText(newText);
        	        	
        	return change;
        }));
    }
    
    public void setModel(AirportsModel newModel)
    {
    	this.model = newModel;
    }
}
