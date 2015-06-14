
public class Fitness {

    /* Public methods */

    // Qui bisogna implementare il valore della soluzione, ora è solo la somma dei valori TODO
	static int getFitness(Individual individual) {
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
    
    // Soluzione migliore possibile.. Per come è stata definita ora è che tutti i geni valgano 20, quindi 20*numGeni
    // Qui vorrei metterci un confronto con la soluzione che cerchiamo per non farlo loopare all'infinito TODO
    static int getMaxFitness() {
        int maxFitness = 20*16;
        return maxFitness;
    }

}
