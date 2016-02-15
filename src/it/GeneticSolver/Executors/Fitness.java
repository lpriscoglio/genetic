package it.GeneticSolver.Executors;
import it.GeneticSolver.Entities.Individual;


/* Public methods for the evaluation of QAP Fitness Functions */
public class Fitness {

    // Evaluation of fitness function for a given individual
	public static int getFitness(Individual individual) {
        int fitness = 0;
        int[][] flows = InstanceGen.getFlows();
        int[][] distances = InstanceGen.getDistances();
        for (int i = 0; i < individual.count(); i++) {
        	for(int j = 0; j < individual.count(); j++){
                fitness+= flows[individual.getGene(i)][individual.getGene(j)]*distances[i][j];
                //System.out.println("Fitness individui "+i+","+j+" con valori "+individual.getGene(i)+"/"+individual.getGene(j)+" data dal valore flow "+flows[individual.getGene(i)][individual.getGene(j)]+" e da distanza "+distances[i][j]);
        	}
        }
        return fitness;
    }
    
    // Best possible solution; TBD
    public static int getMaxFitness() {
        int maxFitness = 20*16;
        return maxFitness;
    }

}
