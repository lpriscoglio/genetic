package it.GeneticSolver.Entities;
import it.GeneticSolver.Executors.Fitness;

import java.util.Arrays;
import java.util.Random;


public class Individual{

	private int possibleGenes;
	private int [] geneSequence;
	private int fitnessVal;
	
	public Individual(int genes, boolean tempInd)
	{
		this.fitnessVal = -1;
		this.possibleGenes = genes;
		this.geneSequence = new int[possibleGenes];
		if(!tempInd)
			initGenes();
	}
	
	public Individual(int genes, int[] genesValues)
	{
		this.fitnessVal = -1;
		this.possibleGenes = genes;
		this.geneSequence = genesValues;
	}

    // Inizializzazione random dei geni di ciascuno
	private void initGenes()
	{
		for (int i = 0; i < possibleGenes; i++) {
            geneSequence[i] = i;
        }    
		Random rnd = new Random();
        for (int i = geneSequence.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          int a = geneSequence[index];
          geneSequence[index] = geneSequence[i];
          geneSequence[i] = a;
        }
	}
	public int getGene(int index)
	{
		return this.geneSequence[index];
	}

	public int [] getGenes()
	{
		return this.geneSequence;
	}
	
	public void setGene(int index, int value)
	{
		this.geneSequence[index] = value;
		this.fitnessVal = -1; //
	}
	
	// Inserisco un nuovo valore in un gene arbitrario, per cui scambio il valore precedente con la posizione 
	// dove era prima il valore nuovo, per mantenere la consistenza
	public void swapGenesByInsertion(int index, int value)
	{
		int oldVal = this.getGene(index); // Che valore c'era prima dove andrà il nuovo
		int oldInd = this.findGeneId(value); //Dove era prima il valore mutato
		this.setGene(index, value);
		this.setGene(oldInd, oldVal);
	}
	
	public int findGeneId(int value)
	{
		for(int i = 0; i<this.count(); i++)
		{
			if(this.getGene(i) == value)
				return i;
		}
		return -1;
	}
	
	public int count()
	{
		return this.possibleGenes;
	}
	
    public int getFitness() 
    {
        if (this.fitnessVal == -1) {
            this.fitnessVal = Fitness.getFitness(this);
        }
        return this.fitnessVal;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.geneSequence);
    }
    @Override
    public Individual clone()
    {
    	try {
    		return (Individual) super.clone();
    	} catch (CloneNotSupportedException e) {
    		throw new RuntimeException(e);
    	}
    }
}
