package it.GeneticSolver.Entities;

public class Population {

	private Individual [] individuals;
	
    public Population(int popSize, int indSize, boolean init) {
        individuals = new Individual[popSize];
        // Initialize population
        if (init) 
        {
            // Loop and create individuals
            for (int i = 0; i < popSize; i++) {
                Individual newIndividual = new Individual(indSize,false);
                saveIndividual(i, newIndividual);
            }
        }
    }
    
    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    // Loop through individuals to find fittest
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < count(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    // Loop through individuals to find worst
    public Individual getWorst() {
        Individual unfittest = individuals[0];
        for (int i = 0; i < count(); i++) {
            if (unfittest.getFitness() <= getIndividual(i).getFitness()) {
                unfittest = getIndividual(i);
            }
        }
        return unfittest;
    }

    // Loop through individuals to find the id of the worst
    public int getWorstId(int start) {
        if(start > count())
        	return count();
        Individual unfittest = individuals[start];
        int result = start;
        for (int i = start; i < count(); i++) {
            if (unfittest.getFitness() <= getIndividual(i).getFitness()) {
                unfittest = getIndividual(i);
                result = i;
            }
        }
        return result;
    }
    
    public Individual getFittest(int start) {
        if(start > count())
        	return individuals[count()];
        Individual fittest = individuals[start];
        // Loop through individuals to find fittest
        for (int i = start; i < count(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    
    public int getFittestId(int start) {
        if(start > count())
        	return count();
        Individual fittest = individuals[start];
        int result = start;
        // Loop through individuals to find fittest
        for (int i = start; i < count(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
                result = i;
            }
        }
        return result;
    }

    /* Public methods */
    // Get population size
    public int count() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual ind) 
    {
        individuals[index] = ind;
    }

}
