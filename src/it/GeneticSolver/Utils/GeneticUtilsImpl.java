package it.GeneticSolver.Utils;

import it.GeneticSolver.Entities.Individual;
import it.GeneticSolver.Entities.Population;

public class GeneticUtilsImpl {

	/* Main hidden and default parameters */
    //private static final double crossChance = 1;
    private static final double mutationRate = 0.1115;
    private static final int tournamentSize = 5; 
    private static final double crossRate = 0.5;
    public static int mutations = 0;
    
    private static final boolean debug = false; 
    private static final boolean debugMutation = false;
    private static final boolean debugCrossover = false;
    
	// Crossover -> Scelta di un individuo base, poi scelgo per ognuno quale aggiungere e scambiare, e da quale genitore
    public static Individual crossover(Individual indiv1, Individual indiv2, int genes) {
    	Individual newSol;
    	int gene = (int) Math.round(Math.random() * (indiv1.count()-1));
    	int [] oldGenes = new int[indiv1.count()];
    	if(Math.random() <= crossRate)
    	{
    		for(int i = 0; i<indiv1.count(); i++)
    		{
    			oldGenes[i] = indiv1.getGene(i);
    		}
    		newSol = new Individual(indiv1.count(), oldGenes);
    		newSol.swapGenesByInsertion(gene, indiv2.getGene(gene));
    	}
    	else
    	{
    		for(int i = 0; i<indiv2.count(); i++)
    		{
    			oldGenes[i] = indiv2.getGene(i);
    		}
    		newSol = new Individual(indiv2.count(), oldGenes);
    		newSol.swapGenesByInsertion(gene, indiv1.getGene(gene));
    	}
        if(debugCrossover)
        {
	        System.out.println("###"+gene);
	        System.out.println("  Parent 1 genes: "+ indiv1.toString());
	        System.out.println("  Parent 2 genes: "+ indiv2.toString());
	        System.out.println("  NewSol genes: "+ newSol.toString());
        }
        return newSol;
    }

    // Semplice scambio random di location in un individuo
    public static void mutateIndividual(Individual indiv, int genes) {
        for (int i = 0; i < genes; i++) {
            if(debugMutation)
            {
                System.out.println("###");
                System.out.println("  Individual was "+indiv.toString());
            }
            if (Math.random() <= mutationRate) 
            {
                // Create random gene
                int gene = (int) Math.round(Math.random() * (indiv.count()-1));
                indiv.swapGenesByInsertion(i, gene);
                if(debugMutation)
                {
                    System.out.println("  Individual "+i+" MUTATED to "+indiv.toString());
                }
                mutations++;
            }
        }
    }

    // Due gruppi di n individui, tra i due migliori di ciascuno viene poi effettuato il crossover
    // Create a tournament population
    // For each place in the tournament get a random individual
    public static Individual tournamentSelection(Population pop, int genes) 
    {
    	Individual fittest;
        Population tournament = new Population(tournamentSize, genes, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.count());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        fittest = tournament.getFittest();
        return fittest;
    }
    
	// Individuals with their chances
    public static double[] getIndividualChances(Population tournament)
    {
    	double [] chances = new double [tournament.count()+1];
    	chances[0] = 0;
    	int totalFitness = 0;
    	for (int i = 0; i<tournament.count(); i++)
    	{
    		totalFitness += tournament.getIndividual(i).getFitness();
    	}
    	for (int i = 0; i<tournament.count(); i++)
    	{
    		chances[i] = (double)tournament.getIndividual(i).getFitness()/totalFitness;
    		if(i>0)
    		{
    			//calcolo la sua fetta di possibilità considerando i precedenti
	    		for (int j = i-1; j>=0; j--)
	        	{
	        		chances[i] += (double)tournament.getIndividual(j).getFitness()/totalFitness;
	        	}
    		}
    	}
    	if(debug)
    	{
	        System.out.println("### TOTAL FITNESS: "+totalFitness);
	    	for (int i = 0; i<tournament.count(); i++)
	    	{
	    		System.out.println("  Individual "+i+" has fitness "+tournament.getIndividual(i).getFitness()+" and has chance "+(double)tournament.getIndividual(i).getFitness()/totalFitness+", so he had to be before "+chances[i]);
	    	}
	        System.out.println("###");
    	}
    	return chances;
    }
    
    // Viene scelto un individuo con probabilità proporzionale alla sua fitness rispetto agli altri.. se è
    // molto grande viene scelto con grande probabilità, etc
	// Select random individual depending on its fitness
    public static Individual proportionalSelection(Population tournament, double [] chances) {
        double randomId = Math.random();
        for (int i = 0; i < tournament.count(); i++) {
        	if(randomId <= chances[i])
        	{
        		if(debug)
        			System.out.println("### WITH RANDOM "+randomId+" the individual "+i+" was selected : had chance "+chances[i]);
        		return tournament.getIndividual(i);
        	}
        }
        System.out.println("HUH? RANDOM "+randomId+" ");
        return tournament.getIndividual(tournament.count()-1); //approssimazioni
    }
}
