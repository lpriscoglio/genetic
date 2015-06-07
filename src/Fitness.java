
public class Fitness {

    /* Public methods */

    // Qui bisogna implementare il valore della soluzione, ora è solo la somma dei valori TODO
    static int getFitness(Individual individual) {
        int fitness = 0;
        for (int i = 0; i < individual.count(); i++) {
                fitness+= individual.getGene(i);
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
