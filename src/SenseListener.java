import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class SenseListener implements ActionListener, ItemListener {
	
	private JTextField genes,instances,population,elitismCount;
	private float [] results; 
	private JProgressBar progressBar;
	private JComboBox<String> instancesChoice;
	private JCheckBox elitism,printing, invert;
	private String selection,newPop;
	private boolean elitismBool;
	private JTextArea result,flows,distances;
	private JRadioButton tournament,proportional,random,replacement,steady;
	private JButton solve,genInst;
	private int elitismCountFinal;
	private final int repeats = 1000;
	
	
	public SenseListener(JTextField genes, JTextField instances, JTextField population, JTextField elitismCount, 
			JProgressBar progressBar, JCheckBox elitism, JCheckBox printing, JCheckBox invert,JTextArea result, JButton solve, JButton genInst, JComboBox<String> instancesChoice,
			JTextArea flows, JTextArea distances, JRadioButton tournament, JRadioButton proportional, JRadioButton random, JRadioButton replacement, JRadioButton steady)
	{
		this.genes = genes;
		this.instances = instances;
		this.population = population;
		this.elitismCount = elitismCount;
		this.instancesChoice = instancesChoice;
		this.genInst = genInst;
		this.invert = invert;
		this.progressBar = progressBar;
		this.elitism = elitism;
		this.printing = printing;
		this.result = result;
		this.solve = solve;
		this.tournament = tournament;
		this.proportional = proportional;
		this.random = random;
		this.replacement = replacement;
		this.steady = steady;
		this.flows = flows;
		this.distances = distances;
		this.elitismBool = false;
		this.elitismCountFinal = 0;
		this.selection = "Tournament";
		this.newPop = "Replacement";
		//this.results = new float [repeats];
	}
	
	//Esecuzione principale algoritmo in thread asincrono
	public void doJob()
	{
			Thread tr = new Thread(new Runnable() {
		        public void run(){
			        int generationCount = 0;
			        int instanceNumber = 0;
			        double tempRes = 0;
		        	solve.setEnabled(false);
		        	long start_time = System.currentTimeMillis();
					long end_time;
					long difference;
					String result = "";
			        progressBar.setMaximum(repeats);
	
					elitismBool = elitism.isSelected();
					if(Integer.parseInt(elitismCount.getText()) <= Integer.parseInt(population.getText()))
							elitismCountFinal = Integer.parseInt(elitismCount.getText());
					else
							elitismCountFinal = 1;
					
					//////////////////////////////////////////////
					selection = "Tournament";
					newPop = "Replacement";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
			    	
			    	/////////////////////////////////////////////////////
			    	result = "";
			    	selection = "Proportional";
					newPop = "Replacement";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="\n\n########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
			    	
			    	//////////////////////////////////////////////
			    	result = "";
			    	selection = "Random";
					newPop = "Replacement";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="\n\n########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
			    	
			    	//////////////////////////////////////////////////////////
			    	result = "";
			    	selection = "Tournament";
					newPop = "Steady State";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="\n\n########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
			    	
			    	////////////////////////////////////////////////////
			    	result = "";
			    	selection = "Proportional";
					newPop = "Steady State";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="\n\n########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
			    	
			    	
			    	/////////////////////////////////////////////////////////
			    	result = "";
			    	selection = "Random";
					newPop = "Steady State";
					start_time = System.currentTimeMillis();
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
			        for( instanceNumber = 0; instanceNumber < repeats; instanceNumber++)
			        {
			        	generationCount = 0;
				        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
				        while (generationCount < Integer.parseInt(instances.getText())) {
				            generationCount++;
				            myPop = GeneticAlg.evolvePopulation(myPop);
				        }
				        //results[instanceNumber] = myPop.getFittest().getFitness();
				        tempRes += myPop.getFittest().getFitness();
			            callUpdateBar(instanceNumber);
			        }
		            callUpdateBar(instanceNumber);
			        tempRes = tempRes/repeats;
			        result+="\n\n########\n";
		        	result+="Using "+instancesChoice.getSelectedItem().toString()+" \n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
			        else
			        	result+="\n";
			        result+="Average solution found: "+tempRes+" over "+repeats+" iterations!\n";
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time taken: "+(difference/1000)+" seconds\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
			    	tempRes = 0;
		        	solve.setEnabled(true);
		        }
		    });
			tr.start();
	}
	
	public void doPrint(String result)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter("result.log", true)));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
	        result+=dateFormat.format(date);
	    	writer.print(result);
	    	writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void genInstance()
	{
		if(invert.isSelected())
			InstanceGen.setInstanceGen(instancesChoice.getSelectedItem().toString(), true);
		else
			InstanceGen.setInstanceGen(instancesChoice.getSelectedItem().toString(), false);
		setNumberField(String.valueOf(InstanceGen.getInstanceCount()));
		setFlowTextField(InstanceGen.printFlows());
		setDistancesTextField(InstanceGen.printDistances());
		solve.setEnabled(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object sourceObj = e.getSource();
		if(sourceObj == elitism)
		{
			elitismBool = elitism.isSelected();
			elitismCountFinal = Integer.parseInt(elitismCount.getText());
			GeneticAlg.initElite(elitismBool, elitismCountFinal);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sourceObj = e.getSource();
		if(sourceObj == solve)
			doJob();
		if(sourceObj == genInst)
			genInstance();
		if(sourceObj == tournament || sourceObj == proportional || sourceObj == random)
			setSelection(e);
		if(sourceObj == replacement || sourceObj == steady)
			setOffspring(e);
	}
	
	public void setSelection(ActionEvent e)
	{
		selection = e.getActionCommand();
		GeneticAlg.init(selection, newPop);
	}
	
	public void setOffspring(ActionEvent e)
	{
		newPop = e.getActionCommand();
		GeneticAlg.init(selection, newPop);
	}
	
	public void callUpdateBar(final int newValue) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              updateBar(newValue);
            }
          });
	}
	
	public void setNumberField(String txt)
	{
		genes.setText(txt);
	}
	
	public void setFlowTextField(String txt)
	{
		flows.setText(txt);
	}

	public void setDistancesTextField(String txt)
	{
		distances.setText(txt);
	}
	
	public void updateBar(int newValue) 
	{
	    progressBar.setValue(newValue);
	}
	

	public void setResult(String txt)
	{
		result.setText(txt);
	}

}
