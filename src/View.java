import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollBar;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.DropMode;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;
import javax.swing.JProgressBar;


public class View extends JFrame 
{
	private JTextField textField;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextArea textArea_2;
	private JProgressBar progressBar;
	private JComboBox comboBox;
	private String selection,newPop;
	private int elitismCount;
	private boolean elitism;
	private JTextField textField_1;
	
	public View() {
		selection = "Tournament";
		newPop = "Replacement";
		elitism = false;
		elitismCount = 1;
		GeneticAlg.init(selection, newPop);
		GeneticAlg.initElite(elitism, elitismCount);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		setTitle("Genetic Alg for QAM");
		Container cont = getContentPane();
		cont.setLayout(null);
		this.setSize(1280, 680);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(594, 11, 656, 35);
		getContentPane().add(panel_1);
		
		JCheckBox chckbxInvertMatrixes = new JCheckBox("Invert Matrixes");
		panel_1.add(chckbxInvertMatrixes);

	    JCheckBox chckbxElitism = new JCheckBox("Elitism");
		
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		panel_1.add(comboBox);
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug12.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug14.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug20.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug25.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had12.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had14.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had18.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had20.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had20.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai17a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai25a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai30a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai40a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai50a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr12a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr20a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr25a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/els19.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc16e.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc32b.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc64a.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tho30.dat");
		comboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tho40.dat");
		
		JButton btnGetInstance = new JButton("Get Instance");
		panel_1.add(btnGetInstance);
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(594, 142, 656, 21);
	    getContentPane().add(progressBar);
		
		JButton btnNewButton = new JButton("Solve");
		btnNewButton.setEnabled(false);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JCheckBox chckbxLog = new JCheckBox("Print Log");
		panel_1.add(chckbxLog);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String result = "";

				elitism = chckbxElitism.isSelected();
				if(Integer.parseInt(textField_1.getText()) <= Integer.parseInt(textField.getText()))
						elitismCount = Integer.parseInt(textField_1.getText());
				else
						elitismCount = Integer.parseInt(textField.getText());
				GeneticAlg.initElite(elitism, elitismCount);
				
		        Population myPop = new Population(Integer.parseInt(textField.getText()), true);
		        for(int i =0;i<Integer.parseInt(textField.getText());i++)
		        {
		        	System.out.print("Individuo "+i+" generato: "+Arrays.toString(myPop.getIndividual(i).getGenes())+ "con fitness "+myPop.getIndividual(i).getFitness());
		        	System.out.println();
		        }
		        int oldMaximum = 0;
		        int repeated = 0;
		        
		        // Evolve our population until we reach an optimum solution
		        int generationCount = 0;
		        while ( myPop.getFittest().getFitness() > 600 && generationCount < 100000) {
		            generationCount++;
		            final int resCount = generationCount;
		            if(oldMaximum == myPop.getFittest().getFitness())
		            {
		            	repeated++;
		            }
		            else
		            {
		            	repeated = 0;
		            	oldMaximum = myPop.getFittest().getFitness();
		            }
		            result+= "Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness()+"\n";
		            myPop = GeneticAlg.evolvePopulation(myPop);
	                SwingUtilities.invokeLater(new Runnable() {
	                  public void run() {
	                    progressBar.setValue(resCount);
	                  }
	                });
		        }
		        result+="########\n";
		        result+="Run on a population of "+textField.getText()+" individuals using "+selection+" selection and "+newPop+" offspring";
		        if(elitism)
		        	result+=" . Using elitism with "+elitismCount+" individuals \n";
		        else
		        	result+="\n";
		        result+="Feasible solution found: "+myPop.getFittest().getFitness()+"! Maximum possible is "+Fitness.getMaxFitness()+"\n";
		        result+="First generation of solution: " + (generationCount-repeated)+"\n";
		        result+="Mutations Count: " + GeneticAlg.mutations+"\n";
		        result+="Genes: "+Arrays.toString((myPop.getFittest().getGenes()));
		        result+=" \n";
		    	setResult(result);
		    	if(chckbxLog.isSelected())
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
			}
		});
		
		btnGetInstance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		    	if(chckbxInvertMatrixes.isSelected())
		    		InstanceGen.setInstanceGen(comboBox.getSelectedItem().toString(), true);
		    	else
		    		InstanceGen.setInstanceGen(comboBox.getSelectedItem().toString(), false);
				setNumberField(String.valueOf(InstanceGen.getInstanceCount()));
				setFlowTextField(InstanceGen.printFlows());
				setDistancesTextField(InstanceGen.printDistances());
				btnNewButton.setEnabled(true);
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(594, 174, 660, 456);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea_2.setRows(30);
		textArea_2.setLineWrap(true);
		
		JScrollPane scr = new JScrollPane(textArea_2);
		panel_2.add(scr, BorderLayout.CENTER);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblResult = new JLabel("Result Output");
		panel_2.add(lblResult, BorderLayout.NORTH);
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(54, 52, 519, 255);
		getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea_1.setRows(15);
		textArea_1.setLineWrap(true);
		
		JLabel lblFlowMatrix = new JLabel("Flow Matrix");
		panel_3.add(lblFlowMatrix, BorderLayout.NORTH);
		lblFlowMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollBar = new JScrollPane(textArea_1);
		scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_3.add(scrollBar, BorderLayout.CENTER);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(54, 318, 519, 312);
		getContentPane().add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea.setLineWrap(true);
		textArea.setRows(10);
		
		JLabel lblDistancesMatrix = new JLabel("Distances Matrix");
		panel_4.add(lblDistancesMatrix, BorderLayout.NORTH);
		lblDistancesMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollBar_1 = new JScrollPane(textArea);
		scrollBar_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_4.add(scrollBar_1, BorderLayout.CENTER);
		scrollBar_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
		panel.setBounds(376, 16, 166, 30);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Number of locations");
		panel.add(lblNewLabel, BorderLayout.WEST);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		textField = new JTextField();
		textField.setEditable(false);
		panel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		panel_5.setBounds(54, 11, 249, 30);
		getContentPane().add(panel_5);
		
		JLabel lblInstanceData = new JLabel("INSTANCE DATA");
		lblInstanceData.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_5.add(lblInstanceData);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		panel_6.setBounds(594, 57, 656, 35);
		getContentPane().add(panel_6);
		
		//Opzioni ALG
		JLabel lblAlgorithmOptions = new JLabel("Parent Selection Method");
		lblAlgorithmOptions.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_6.add(lblAlgorithmOptions);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Tournament");
		rdbtnNewRadioButton.setSelected(true);
		panel_6.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Proportional");
		panel_6.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnRandom = new JRadioButton("Random");
		panel_6.add(rdbtnRandom);
		
	    ButtonGroup group = new ButtonGroup();
	    ButtonGroup group_1 = new ButtonGroup();
	    group.add(rdbtnNewRadioButton);
	    group.add(rdbtnNewRadioButton_1);
	    group.add(rdbtnRandom);
	    
	    Component horizontalGlue = Box.createHorizontalGlue();
	    panel_6.add(horizontalGlue);
	    
	    Component rigidArea = Box.createRigidArea(new Dimension(25, 20));
	    panel_6.add(rigidArea);
	    
	    panel_6.add(chckbxElitism);
	    
	    textField_1 = new JTextField();
	    textField_1.setText(Integer.toString(elitismCount));
	    panel_6.add(textField_1);
	    textField_1.setColumns(5);
	    
	    chckbxElitism.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				elitism = chckbxElitism.isSelected();
				elitismCount = Integer.parseInt(textField_1.getText());
				GeneticAlg.initElite(elitism, elitismCount);
			}
		});
	    
	    /*textField_1.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				elitism = chckbxElitism.isSelected();
				if(Integer.parseInt(textField_1.getText()) <= Integer.parseInt(textField.getText()))
						elitismCount = Integer.parseInt(textField_1.getText());
				else
						elitismCount = Integer.parseInt(textField.getText());
				GeneticAlg.initElite(elitism, elitismCount);
			}
			public void removeUpdate(DocumentEvent arg0) {
				elitism = chckbxElitism.isSelected();
				if(Integer.parseInt(textField_1.getText()) <= Integer.parseInt(textField.getText()))
					elitismCount = Integer.parseInt(textField_1.getText());
			else
					elitismCount = Integer.parseInt(textField.getText());
				GeneticAlg.initElite(elitism, elitismCount);
			}
			public void insertUpdate(DocumentEvent arg0) {
				elitism = chckbxElitism.isSelected();
				if(Integer.parseInt(textField_1.getText()) <= Integer.parseInt(textField.getText()))
					elitismCount = Integer.parseInt(textField_1.getText());
				else
					elitismCount = Integer.parseInt(textField.getText());
				GeneticAlg.initElite(elitism, elitismCount);
			}
		});*/
	    
	    rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selection = arg0.getActionCommand();
				GeneticAlg.init(selection, newPop);
			}
		});
	    rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selection = arg0.getActionCommand();
				GeneticAlg.init(selection, newPop);
			}
		});
	    rdbtnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selection = arg0.getActionCommand();
				GeneticAlg.init(selection, newPop);
			}
		});
	    
	    JPanel panel_7 = new JPanel();
	    panel_7.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
	    panel_7.setBounds(594, 96, 656, 35);
	    getContentPane().add(panel_7);
	    
	    JLabel label = new JLabel("Offspring Options");
	    label.setFont(new Font("Tahoma", Font.BOLD, 13));
	    panel_7.add(label);
	    
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Replacement");
		panel_7.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setSelected(true);
		
	    group_1.add(rdbtnNewRadioButton_2);
	    
	    JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Steady State");
	    panel_7.add(rdbtnNewRadioButton_3);
	    group_1.add(rdbtnNewRadioButton_3);
	    
	    rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newPop = arg0.getActionCommand();
				GeneticAlg.init(selection, newPop);
			}
		});
	    
	    rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newPop = arg0.getActionCommand();
				GeneticAlg.init(selection, newPop);
			}
		});
		
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	

	public void setNumberField(String txt)
	{
		textField.setText(txt);
	}
	
	public void setFlowTextField(String txt)
	{
		textArea_1.setText(txt);
	}

	public void setDistancesTextField(String txt)
	{
		textArea.setText(txt);
	}
	
	  public void updateBar(int newValue) {
		    progressBar.setValue(newValue);
		  }
	

	public void setResult(String txt)
	{
		textArea_2.setText(txt);
	}
}
