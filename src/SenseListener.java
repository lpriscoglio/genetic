import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
	private JProgressBar progressBar;
	private JComboBox<String> instancesChoice;
	private JCheckBox elitism,printing, invert;
	private String selection,newPop;
	private boolean elitismBool;
	private JTextArea result,flows,distances;
	private JRadioButton tournament,proportional,random,replacement,steady;
	private JButton solve,genInst;
	private int elitismCountFinal;
	
	
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
	}
	
	//Esecuzione principale algoritmo in thread asincrono
	public void doJob()
	{
			Thread tr = new Thread(new Runnable() {
		        public void run(){
		        	solve.setEnabled(false);
		        	long start_time = System.currentTimeMillis();
					long end_time;
					long difference;
					String result = "";
			        progressBar.setMaximum(Integer.parseInt(instances.getText()));
	
					elitismBool = elitism.isSelected();
					if(Integer.parseInt(elitismCount.getText()) <= Integer.parseInt(population.getText()))
							elitismCountFinal = Integer.parseInt(elitismCount.getText());
					else
							elitismCountFinal = 1;
					GeneticAlg.init(selection, newPop);
					GeneticAlg.initElite(elitismBool, elitismCountFinal);
					
			        Population myPop = new Population(Integer.parseInt(population.getText()),Integer.parseInt(genes.getText()), true);
			        for(int i =0;i<Integer.parseInt(population.getText());i++)
			        {
			        	result+="Individuo "+(i+1)+" generato: "+Arrays.toString(myPop.getIndividual(i).getGenes())+ ". Ha valore di fitness "+myPop.getIndividual(i).getFitness()+"\n";
			        }
			        int oldMaximum = 0;
			        int repeated = 0;
			        int improvements = 0;
			        result+="\n";
			        
			        // Evolve our population until we reach an optimum solution
			        int generationCount = 0;
			        while (generationCount < Integer.parseInt(instances.getText())) {
			            generationCount++;
			            if(oldMaximum == myPop.getFittest().getFitness())
			            {
			            	repeated++;
			            }
			            else
			            {
			            	repeated = 0;
			            	improvements++;
			            	oldMaximum = myPop.getFittest().getFitness();
			            }
			            //result+= "Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness()+"\n";
			            myPop = GeneticAlg.evolvePopulation(myPop);
			            callUpdateBar(generationCount);
			        }
			        result+="########\n";
			        result+="Run on a population of "+population.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
			        if(elitismBool)
			        	result+=" . Using elitism with "+elitismCountFinal+" individuals \n";
			        else
			        	result+="\n";
			        result+="Feasible solution found: "+myPop.getFittest().getFitness()+"!\n";
			        result+="First generation of solution: " + (generationCount-repeated)+"\n";
			        result+="Improvements Count: " + improvements+"\n";
			        result+="Genes: "+Arrays.toString((myPop.getFittest().getGenes()));
			        result+=" \n";
					end_time = System.currentTimeMillis();
					difference = end_time-start_time;
			        result+=" Total time: "+(difference/1000)+" s\n";
			    	setResult(result);
			    	if(printing.isSelected())
			    	{
				    	doPrint(result);
			    	}
		        	solve.setEnabled(true);
		        }
		    });
			tr.start();
	}
	
	public void doPrint(String result)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter("result.log", "UTF-8");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
	        result+=dateFormat.format(date);
	    	writer.print(result);
	    	writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
