package it.GeneticSolver.Listeners;
import it.GeneticSolver.Entities.Population;
import it.GeneticSolver.Executors.GeneticAlg;
import it.GeneticSolver.Executors.InstanceGen;
import it.GeneticSolver.GUI.View;

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
import java.util.Arrays;
import java.util.Date;

import javax.swing.SwingUtilities;


public class SenseListener implements ActionListener, ItemListener {
	
	private int elitismCountFinal;
	private String selection,newPop;
	private boolean elitismBool;
	private View view;	
	
	public SenseListener(View view)
	{
		this.view = view;
		selection = "Tournament";
		newPop = "Replacement";
	}
	
	//Esecuzione principale algoritmo in thread asincrono
	public void doJob()
	{
			Thread tr = new Thread(new Runnable() {
		        public void run()
		        {
		        	doJobInternal();
		        }
		    });
			tr.start();
	}
	
	private void doJobInternal()
	{
    	view.solveButton.setEnabled(false);
    	long start_time = System.currentTimeMillis();
		long end_time;
		long difference;
		String result = "";
        view.progressBar.setMaximum(Integer.parseInt(view.textField_Iterations.getText())-1);

		elitismBool = view.chckbxElitism.isSelected();
		if(Integer.parseInt(view.textField_ElitismCount.getText()) <= Integer.parseInt(view.textField_Indiv.getText()))
				elitismCountFinal = Integer.parseInt(view.textField_ElitismCount.getText());
		else
				elitismCountFinal = 1;

		start_time = System.currentTimeMillis();
		GeneticAlg.init(selection, newPop); //Change
		GeneticAlg.initElite(elitismBool, elitismCountFinal);
        Population myPop = new Population(Integer.parseInt(view.textField_Indiv.getText()),Integer.parseInt(view.textField_Locations.getText()), true);
        for ( int i = 0; i < Integer.parseInt(view.textField_Iterations.getText()); i++) 
        {
            myPop = GeneticAlg.evolvePopulation(myPop);
            callUpdateBar(i);
        }
        result+="########\n";
    	result+="Using "+view.selectableProblemsComboBox.getSelectedItem().toString()+" \n";
        result+="Run on a population of "+view.textField_Indiv.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
        if(elitismBool)
        	result+=" . Using elitism with "+elitismCountFinal+" individual(s) \n";
        else
        	result+="\n";
        result+="Feasible solution found: "+myPop.getFittest().getFitness()+" over "+view.textField_Iterations.getText()+" iterations!\n";
        result+="Genes: "+Arrays.toString((myPop.getFittest().getGenes()));
        result+=" \n";
		end_time = System.currentTimeMillis();
		difference = end_time-start_time;
        result+="Total time taken: "+(difference/1000)+" seconds\n";
    	setResult(result);
    	if(view.chckbxLog.isSelected())
    	{
	    	doPrint(result);
    	}
    	view.solveButton.setEnabled(true);
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
		if(view.chckbxInvertMatrixes.isSelected())
			InstanceGen.setInstanceGen(view.selectableProblemsComboBox.getSelectedItem().toString(), true);
		else
			InstanceGen.setInstanceGen(view.selectableProblemsComboBox.getSelectedItem().toString(), false);
		setNumberField(String.valueOf(InstanceGen.getInstanceCount()));
		setFlowTextField(InstanceGen.printFlows());
		setDistancesTextField(InstanceGen.printDistances());
		view.solveButton.setEnabled(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object sourceObj = e.getSource();
		if(sourceObj == view.chckbxElitism)
		{
			elitismBool = view.chckbxElitism.isSelected();
			elitismCountFinal = Integer.parseInt(view.textField_ElitismCount.getText());
			GeneticAlg.initElite(elitismBool, elitismCountFinal);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sourceObj = e.getSource();
		if(sourceObj == view.solveButton)
			doJob();
		if(sourceObj == view.btnGetInstance)
			genInstance();
		if(sourceObj == view.rdbtnTournament || sourceObj == view.rdbtnProportional || sourceObj == view.rdbtnRandom)
			setSelection(e);
		if(sourceObj == view.rdbtnReplacement || sourceObj == view.rdbtnSteady)
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
		view.textField_Locations.setText(txt);
	}
	
	public void setFlowTextField(String txt)
	{
		view.flowArea.setText(txt);
	}

	public void setDistancesTextField(String txt)
	{
		view.distancesArea.setText(txt);
	}
	
	public void updateBar(int newValue) 
	{
	    view.progressBar.setValue(newValue);
	}
	

	public void setResult(String txt)
	{
		view.resultArea.setText(txt);
	}

}
