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
	private JTextField textField_Locations;
	private JTextArea distancesArea;
	private JTextArea flowArea;
	private JTextArea resultArea;
	private JProgressBar progressBar;
	private JComboBox<String> comboBox;
	private JCheckBox chckbxLog, chckbxElitism;
	private String selection,newPop;
	private JTextField textField_ElitismCount;
	private JTextField textField_Indiv;
	private JTextField textField_Iterations;
	private JRadioButton rdbtnTournament, rdbtnProportional, rdbtnRandom, rdbtnReplacement, rdbtnSteady;
	private JCheckBox chckbxInvertMatrixes;
	private JButton btnGetInstance, btnNewButton;
	private SenseListener senseList;
	
	public View() {		
		selection = "Tournament";
		newPop = "Replacement";
		GeneticAlg.init(selection, newPop);
		
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		setTitle("Genetic Alg for QAM");
		Container cont = getContentPane();
		cont.setLayout(null);
		this.setSize(1280, 680);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(594, 11, 656, 35);
		getContentPane().add(panel_1);
		
		chckbxInvertMatrixes = new JCheckBox("Invert Matrixes");
		panel_1.add(chckbxInvertMatrixes);

	    chckbxElitism = new JCheckBox("Elitism");
		
		comboBox = new JComboBox<String>();
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
		
		btnGetInstance = new JButton("Get Instance");
		panel_1.add(btnGetInstance);
	    
	    progressBar = new JProgressBar();
	    progressBar.setBounds(594, 142, 656, 21);
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
	    getContentPane().add(progressBar);
		
		btnNewButton = new JButton("Solve");
		btnNewButton.setEnabled(false);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(594, 174, 660, 456);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		resultArea.setRows(30);
		resultArea.setLineWrap(true);
		
		JScrollPane scr = new JScrollPane(resultArea);
		panel_2.add(scr, BorderLayout.CENTER);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblResult = new JLabel("Result Output");
		panel_2.add(lblResult, BorderLayout.NORTH);
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(54, 52, 519, 255);
		getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		flowArea = new JTextArea();
		flowArea.setEditable(false);
		flowArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flowArea.setRows(15);
		flowArea.setLineWrap(false);
		
		JLabel lblFlowMatrix = new JLabel("Flow Matrix");
		panel_3.add(lblFlowMatrix, BorderLayout.NORTH);
		lblFlowMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollBar = new JScrollPane(flowArea);
		scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_3.add(scrollBar, BorderLayout.CENTER);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(54, 318, 519, 312);
		getContentPane().add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		distancesArea = new JTextArea();
		distancesArea.setEditable(false);
		distancesArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		distancesArea.setLineWrap(false);
		distancesArea.setRows(10);
		
		JLabel lblDistancesMatrix = new JLabel("Distances Matrix");
		panel_4.add(lblDistancesMatrix, BorderLayout.NORTH);
		lblDistancesMatrix.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollBar_1 = new JScrollPane(distancesArea);
		scrollBar_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_4.add(scrollBar_1, BorderLayout.CENTER);
		scrollBar_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
		panel.setBounds(324, 16, 62, 30);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Locations");
		panel.add(lblNewLabel, BorderLayout.WEST);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		textField_Locations = new JTextField();
		textField_Locations.setEditable(false);
		panel.add(textField_Locations, BorderLayout.SOUTH);
		textField_Locations.setColumns(10);
		
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
		
		rdbtnTournament = new JRadioButton("Tournament");
		rdbtnTournament.setSelected(true);
		panel_6.add(rdbtnTournament);
		
		rdbtnProportional = new JRadioButton("Proportional");
		panel_6.add(rdbtnProportional);
		
		rdbtnRandom = new JRadioButton("Random");
		panel_6.add(rdbtnRandom);
		
	    ButtonGroup group = new ButtonGroup();
	    ButtonGroup group_1 = new ButtonGroup();
	    group.add(rdbtnTournament);
	    group.add(rdbtnProportional);
	    group.add(rdbtnRandom);
	    
	    Component horizontalGlue = Box.createHorizontalGlue();
	    panel_6.add(horizontalGlue);
	    
	    Component rigidArea = Box.createRigidArea(new Dimension(25, 20));
	    panel_6.add(rigidArea);
	    
	    panel_6.add(chckbxElitism);
	    
	    textField_ElitismCount = new JTextField();
	    textField_ElitismCount.setText("1");
	    panel_6.add(textField_ElitismCount);
	    textField_ElitismCount.setColumns(5);
	    
	    
	    
	    JPanel panel_7 = new JPanel();
	    panel_7.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
	    panel_7.setBounds(594, 96, 656, 35);
	    getContentPane().add(panel_7);
	    
	    JLabel label = new JLabel("Offspring Options");
	    label.setFont(new Font("Tahoma", Font.BOLD, 13));
	    panel_7.add(label);
	    
		rdbtnReplacement = new JRadioButton("Replacement");
		panel_7.add(rdbtnReplacement);
		rdbtnReplacement.setSelected(true);
		
	    group_1.add(rdbtnReplacement);
	    
	    rdbtnSteady = new JRadioButton("Steady State");
	    panel_7.add(rdbtnSteady);
	    group_1.add(rdbtnSteady);
	    
	    chckbxLog = new JCheckBox("Print Log");
	    panel_7.add(chckbxLog);
	    
	    JPanel panel_8 = new JPanel();
	    panel_8.setBounds(396, 16, 75, 30);
	    getContentPane().add(panel_8);
	    panel_8.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblIndividuals = new JLabel("Individuals");
	    lblIndividuals.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panel_8.add(lblIndividuals, BorderLayout.NORTH);
	    
	    textField_Indiv = new JTextField();
	    textField_Indiv.setText("40");
	    panel_8.add(textField_Indiv, BorderLayout.CENTER);
	    textField_Indiv.setColumns(10);
	    
	    JPanel panel_9 = new JPanel();
	    panel_9.setBounds(479, 16, 75, 30);
	    getContentPane().add(panel_9);
	    panel_9.setLayout(new BorderLayout(0, 0));
	    
	    JLabel lblIterations = new JLabel("Iterations");
	    lblIterations.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panel_9.add(lblIterations, BorderLayout.NORTH);
	    
	    textField_Iterations = new JTextField();
	    textField_Iterations.setText("5000");
	    textField_Iterations.setColumns(10);
	    panel_9.add(textField_Iterations, BorderLayout.CENTER);
	    
		
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		senseList = new SenseListener(textField_Locations, textField_Iterations, textField_Indiv, textField_ElitismCount, progressBar,
				chckbxElitism,chckbxLog,chckbxInvertMatrixes, resultArea, btnNewButton, btnGetInstance, comboBox, flowArea, distancesArea,
				rdbtnTournament, rdbtnProportional, rdbtnRandom, rdbtnReplacement, rdbtnSteady);
		
	    rdbtnTournament.addActionListener(senseList);
	    rdbtnProportional.addActionListener(senseList);
	    rdbtnRandom.addActionListener(senseList);
	    rdbtnSteady.addActionListener(senseList);
	    rdbtnReplacement.addActionListener(senseList);
		btnNewButton.addActionListener(senseList);
		btnGetInstance.addActionListener(senseList);
	    chckbxElitism.addItemListener(senseList);
	}
	
}
