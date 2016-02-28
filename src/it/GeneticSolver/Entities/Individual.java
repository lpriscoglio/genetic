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

	/***
	 * Inizializzazione random dei geni dell'individuo
	 */
    public void initGenes()
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
		int currIndex = checkConsistency(index);
		return this.geneSequence[currIndex];
	}

	public int [] getGenes()
	{
		return this.geneSequence;
	}
	
	public void setGene(int index, int value)
	{
		int currValue = checkConsistency(value);
		int currIndex = checkConsistency(index);
		this.geneSequence[currIndex] = currValue;
		this.fitnessVal = -1; //
	}
	
	/***
	 * 
	 * Per mantenere la consistenza, inserire un valore a un indice significa scambiare di posizione il valore
	 * attualmente presente nella posizione index con la posizione in cui è già presente il valore value
	 * @param index
	 * @param value
	 */
	
	public void swapGenesByInsertion(int index, int value)
	{
		int currValue = checkConsistency(value);
		int currIndex = checkConsistency(index);
		int oldVal = this.getGene(currIndex); // Che valore c'era prima dove andrà il nuovo
		int oldInd = this.findGeneId(currValue); //Dove era prima il valore mutato
		this.setGene(currIndex, currValue);
		this.setGene(oldInd, oldVal);
	}
	
	public int findGeneId(int value)
	{
		int currValue = checkConsistency(value);
		for(int i = 0; i<this.count(); i++)
		{
			if(this.getGene(i) == currValue)
				return i;
		}
		return -1;
	}
	
	public int count()
	{
		return this.possibleGenes;
	}
	
	/***
	 * Ottengo la fitness dell'individuo, per convenzione con valore -1 non è stata ancora calcolata
	 * o i geni sono stati modificati, quindi viene ricalcolata by need.
	 * @return
	 */
    public int getFitness() 
    {
        if (this.fitnessVal == -1) {
            this.fitnessVal = Fitness.getFitness(this);
        }
        return this.fitnessVal;
    }
    
    /***
     * Geni e posizioni possono essere solo nell'intervallo 0 : n-1
     * @param toCheck
     * @return
     */
    private int checkConsistency(int toCheck)
    {
    	if(toCheck < 0)
    		return 0;
    	if(toCheck >= this.possibleGenes)
    		return this.possibleGenes-1;
    	return toCheck;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.geneSequence);
    }
}
