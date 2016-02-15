package it.GeneticSolver.GUI;
import it.GeneticSolver.Executors.GeneticAlg;
import it.GeneticSolver.Listeners.SenseListener;

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
import javax.swing.WindowConstants;

import java.awt.Container;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JCheckBox;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;

import javax.swing.JProgressBar;


public class View extends JFrame 
{
	/**
	 * Rappresenta la finestra principale
	 */
	private static final long serialVersionUID = 1L;
	public JTextField textField_Locations;
	public JTextArea distancesArea;
	public JTextArea flowArea;
	public JTextArea resultArea;
	public JProgressBar progressBar;
	public JComboBox<String> selectableProblemsComboBox;
	public JCheckBox chckbxLog, chckbxElitism;
	public String selection,newPop;
	public JTextField textField_ElitismCount;
	public JTextField textField_Indiv;
	public JTextField textField_Iterations;
	public JRadioButton rdbtnTournament, rdbtnProportional, rdbtnRandom, rdbtnReplacement, rdbtnSteady;
	public JCheckBox chckbxInvertMatrixes;
	public JButton btnGetInstance, solveButton;
	private SenseListener senseList;
	
	public View() {		
		selection = "Tournament";
		newPop = "Replacement";
		GeneticAlg.init(selection, newPop);
		
		/*
		 * Main Window
		 */
		
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		setTitle("Genetic Alg for QAM");
		Container cont = getContentPane();
		cont.setLayout(null);
		this.setSize(1280, 680);
		
		/*
		 * Pannello istanza
		 */
		
		JPanel istancePanel = new JPanel();
		istancePanel.setBounds(594, 11, 656, 35);
		getContentPane().add(istancePanel);
		
		chckbxInvertMatrixes = new JCheckBox("Invert Matrixes");
		istancePanel.add(chckbxInvertMatrixes);

	    chckbxElitism = new JCheckBox("Elitism");
		
		selectableProblemsComboBox = new JComboBox<String>();
		selectableProblemsComboBox.setEditable(true);
		istancePanel.add(selectableProblemsComboBox);
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug12.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug14.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug20.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/nug25.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had12.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had14.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had18.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had20.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/had20.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai17a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai25a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai30a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai40a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tai50a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr12a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr20a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/chr25a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/els19.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc16e.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc32b.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/esc64a.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tho30.dat");
		selectableProblemsComboBox.addItem("http://anjos.mgi.polymtl.ca/qaplib/data.d/tho40.dat");
		
		btnGetInstance = new JButton("Get Instance");
		istancePanel.add(btnGetInstance);
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(594, 142, 656, 21);
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
	    getContentPane().add(progressBar);
		
		solveButton = new JButton("Solve");
		solveButton.setEnabled(false);
		istancePanel.add(solveButton);
		solveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		/*
		 * Pannello Risultati
		 */
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBounds(594, 174, 660, 456);
		getContentPane().add(resultPanel);
		resultPanel.setLayout(new BorderLayout(0, 0));
		
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		resultArea.setRows(30);
		resultArea.setLineWrap(true);
		
		JScrollPane resultScroller = new JScrollPane(resultArea);
		resultPanel.add(resultScroller, BorderLayout.CENTER);
		resultScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblResult = new JLabel("Result Output");
		resultPanel.add(lblResult, BorderLayout.NORTH);
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		/*
		 * Pannello Flows
		 */
		
		JPanel flowPanel = new JPanel();
		flowPanel.setBounds(54, 52, 519, 255);
		getContentPane().add(flowPanel);
		flowPanel.setLayout(new BorderLayout(0, 0));
		
		flowArea = new JTextArea();
		flowArea.setEditable(false);
		flowArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flowArea.setRows(15);
		flowArea.setLineWrap(false);
		
		JLabel lblFlowMatrix = new JLabel("Flow Matrix");
		flowPanel.add(lblFlowMatrix, BorderLayout.NORTH);
		lblFlowMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane flowScroller = new JScrollPane(flowArea);
		flowScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		flowPanel.add(flowScroller, BorderLayout.CENTER);
		flowScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		/*
		 * Pannello Distances
		 */
		
		JPanel distancesPanel = new JPanel();
		distancesPanel.setBounds(54, 318, 519, 312);
		getContentPane().add(distancesPanel);
		distancesPanel.setLayout(new BorderLayout(0, 0));
		
		distancesArea = new JTextArea();
		distancesArea.setEditable(false);
		distancesArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		distancesArea.setLineWrap(false);
		distancesArea.setRows(10);
		
		JLabel lblDistancesMatrix = new JLabel("Distances Matrix");
		distancesPanel.add(lblDistancesMatrix, BorderLayout.NORTH);
		lblDistancesMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane distancesScroller = new JScrollPane(distancesArea);
		distancesScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		distancesPanel.add(distancesScroller, BorderLayout.CENTER);
		distancesScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		/*
		 * Pannelli TOP
		 */
		
		JPanel locPanel = new JPanel();
		locPanel.setBounds(324, 16, 62, 30);
		getContentPane().add(locPanel);
		locPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel locationLabel = new JLabel("Locations");
		locPanel.add(locationLabel, BorderLayout.WEST);
		locationLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		textField_Locations = new JTextField();
		textField_Locations.setEditable(false);
		locPanel.add(textField_Locations, BorderLayout.SOUTH);
		textField_Locations.setColumns(10);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		titlePanel.setBounds(54, 11, 249, 30);
		getContentPane().add(titlePanel);
		
		JPanel indivPanel = new JPanel();
	    indivPanel.setBounds(396, 16, 75, 30);
	    getContentPane().add(indivPanel);
	    indivPanel.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblIndividuals = new JLabel("Individuals");
	    lblIndividuals.setFont(new Font("Tahoma", Font.BOLD, 11));
	    indivPanel.add(lblIndividuals, BorderLayout.NORTH);
	    
	    textField_Indiv = new JTextField();
	    textField_Indiv.setText("40");
	    indivPanel.add(textField_Indiv, BorderLayout.CENTER);
	    textField_Indiv.setColumns(10);
	    
	    JPanel iterationPanel = new JPanel();
	    iterationPanel.setBounds(479, 16, 75, 30);
	    getContentPane().add(iterationPanel);
	    iterationPanel.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblIterations = new JLabel("Iterations");
	    lblIterations.setFont(new Font("Tahoma", Font.BOLD, 11));
	    iterationPanel.add(lblIterations, BorderLayout.NORTH);
	    
	    textField_Iterations = new JTextField();
	    textField_Iterations.setText("5000");
	    textField_Iterations.setColumns(10);
	    iterationPanel.add(textField_Iterations, BorderLayout.CENTER);
		
		JLabel lblInstanceData = new JLabel("INSTANCE DATA");
		lblInstanceData.setFont(new Font("Tahoma", Font.BOLD, 16));
		titlePanel.add(lblInstanceData);
		
		/*
		 * Pannello opzioni su scelta genitori
		 */
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		selectionPanel.setBounds(594, 57, 656, 35);
		getContentPane().add(selectionPanel);
		
		
		JLabel lblAlgorithmOptions = new JLabel("Parent Selection Method");
		lblAlgorithmOptions.setFont(new Font("Tahoma", Font.BOLD, 13));
		selectionPanel.add(lblAlgorithmOptions);
		
		rdbtnTournament = new JRadioButton("Tournament");
		rdbtnTournament.setSelected(true);
		selectionPanel.add(rdbtnTournament);
		
		rdbtnProportional = new JRadioButton("Proportional");
		selectionPanel.add(rdbtnProportional);
		
		rdbtnRandom = new JRadioButton("Random");
		selectionPanel.add(rdbtnRandom);
		
	    ButtonGroup selectionGroup = new ButtonGroup();
	    ButtonGroup offspringGroup = new ButtonGroup();
	    selectionGroup.add(rdbtnTournament);
	    selectionGroup.add(rdbtnProportional);
	    selectionGroup.add(rdbtnRandom);
	    
	    Component horizontalGlue = Box.createHorizontalGlue();
	    selectionPanel.add(horizontalGlue);
	    
	    Component rigidArea = Box.createRigidArea(new Dimension(25, 20));
	    selectionPanel.add(rigidArea);
	    
	    selectionPanel.add(chckbxElitism);
	    
	    textField_ElitismCount = new JTextField();
	    textField_ElitismCount.setText("1");
	    selectionPanel.add(textField_ElitismCount);
	    textField_ElitismCount.setColumns(5);
	    
	    /*
	     * Pannello opzioni scelta offspring
	     */
	    
	    JPanel offspringPanel = new JPanel();
	    offspringPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
	    offspringPanel.setBounds(594, 96, 656, 35);
	    getContentPane().add(offspringPanel);
	    
	    JLabel label = new JLabel("Offspring Options");
	    label.setFont(new Font("Tahoma", Font.BOLD, 13));
	    offspringPanel.add(label);
	    
		rdbtnReplacement = new JRadioButton("Replacement");
		offspringPanel.add(rdbtnReplacement);
		rdbtnReplacement.setSelected(true);
		
	    offspringGroup.add(rdbtnReplacement);
	    
	    rdbtnSteady = new JRadioButton("Steady State");
	    offspringPanel.add(rdbtnSteady);
	    offspringGroup.add(rdbtnSteady);
	    
	    chckbxLog = new JCheckBox("Print Log");
	    offspringPanel.add(chckbxLog);
	    
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		senseList = new SenseListener(this);
		
	    rdbtnTournament.addActionListener(senseList);
	    rdbtnProportional.addActionListener(senseList);
	    rdbtnRandom.addActionListener(senseList);
	    rdbtnSteady.addActionListener(senseList);
	    rdbtnReplacement.addActionListener(senseList);
		solveButton.addActionListener(senseList);
		btnGetInstance.addActionListener(senseList);
	    chckbxElitism.addItemListener(senseList);
	}
	
}
