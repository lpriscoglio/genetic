package it.GeneticSolver.Executors;
import it.GeneticSolver.Entities.Individual;
import it.GeneticSolver.Entities.Population;
import it.GeneticSolver.Utils.GeneticUtilsImpl;

/***
 *  This static class defines the functions that solve the QAP, by swapping the genes and creating new populations
 *  */
public class GeneticAlg {

    private static boolean elitism = false;
    private static int elitismCount = 1; 
    private static String selection = "Tournament"; //Possible values: Tournament / Proportional / Random
    private static String newPopSelection = "Replacement"; //Possible values: Replacement / Steady State
    private static int elitismOffset = 0;
    private static int geneCount = 0;
    

    /* Public methods */
    
    // Evolution
    public static Population evolvePopulation(Population pop) {
    	double [] chancesEagerCalc = GeneticUtilsImpl.getIndividualChances(pop);
        geneCount = pop.getIndividual(0).count();
        Population newPopulation = new Population(pop.count(), geneCount, false);
        newPopulation = pop;
        // Elitism maintains always the #elitismCount best individuals
        if (elitism) {
        	Individual temp;
        	for(int i=0; i<elitismCount; i++)
        	{
        		int oldBest = newPopulation.getFittestId(i);
        		temp = newPopulation.getIndividual(i);
        		newPopulation.saveIndividual(i, newPopulation.getFittest(i));
        		newPopulation.saveIndividual(oldBest, temp);
        	}
        	elitismOffset = elitismCount;
        }

        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.count(); i++) {
        	Individual ind1,ind2;
        	
        	if(selection == "Tournament")
        	{
        		ind1 = GeneticUtilsImpl.tournamentSelection(pop, geneCount);
        		ind2 = GeneticUtilsImpl.tournamentSelection(pop, geneCount);
        	}
        	else if(selection == "Proportional")
        	{
            	ind1 = GeneticUtilsImpl.proportionalSelection(pop, chancesEagerCalc);
            	ind2 = GeneticUtilsImpl.proportionalSelection(pop, chancesEagerCalc);
        	}
        	else
        	{
                int i1 = (int) (Math.random() * pop.count());
                int i2 = (int) (Math.random() * pop.count());

                ind1 = pop.getIndividual(i1);
                ind2 = pop.getIndividual(i2);
        	}
                
            Individual newIndiv = GeneticUtilsImpl.crossover(ind1, ind2, geneCount);
            if(newPopSelection == "Replacement")
            	newPopulation.saveIndividual(i, newIndiv);
            else if(newPopSelection == "Steady State")
            {
            	if(newPopulation.getWorst().getFitness() > newIndiv.getFitness())
            		newPopulation.saveIndividual(newPopulation.getWorstId(elitismOffset), newIndiv);
            }
            else
            	newPopulation.saveIndividual(i, newIndiv);
        }
        
        // Mutazioni random su tutti ma non sul migliore
        for (int i = elitismOffset; i < newPopulation.count(); i++) 
        {
        	GeneticUtilsImpl.mutateIndividual(newPopulation.getIndividual(i), geneCount); 
        }

        return newPopulation;
    }

	public static void init(String string, String string2) {
		selection = string;
		newPopSelection = string2;
	}
	
	public static void initElite(boolean el, int elCount) {
		elitism = el;
		elitismCount = elCount;
	}
}
