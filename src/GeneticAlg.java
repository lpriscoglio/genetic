
public class GeneticAlg {

	
	/* GA parameters */
    private static final double crossRate = 0.5;
    private static final double crossChance = 1;
    private static final double mutationRate = 0.0115;
    private static final int tournamentSize = 5; // Velocità di convergenza?
    private static boolean elitism = false;
    private static int elitismCount = 1; // da testare, sembra ok TODO
    private static String selection = "Proportional"; //tournament / proportional / random
    private static String newPopSelection = "Steady"; //replacement / steady 
    private static final boolean debug = false;
    private static int elitismOffset = 0;

    /* Public methods */
    
    // Evoluzione
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.count(), false);
        newPopulation = pop;

        // Con l'elitism tengo sempre il migliore individuo a prescindere
        if (elitism) {
        	Individual temp;
        	for(int i=0; i<elitismCount; i++)
        	{
        		int oldBest = newPopulation.getFittestId(i);
        		temp = newPopulation.getIndividual(i);
        		newPopulation.saveIndividual(i, newPopulation.getFittest(i));
        		newPopulation.saveIndividual(oldBest, temp);
        	}
        }

        if(elitism)
        {
        	elitismOffset = elitismCount;
        }
        // Loop over the population size and create new individuals with
        // crossover
        // Fare la compare tra 1 e 2 e dare un vantaggio al migliore?
        for (int i = elitismOffset; i < pop.count(); i++) {
        	Individual ind1,ind2;
        	
        	if(selection == "Tournament")
        	{
        		ind1 = tournamentSelection(pop);
        		ind2 = tournamentSelection(pop);
        	}
        	else if(selection == "Proportional")
        	{
            	ind1 = proportionalSelection(pop);
            	ind2 = proportionalSelection(pop);
        	}
        	else
        	{
                int i1 = (int) (Math.random() * pop.count());
                int i2 = (int) (Math.random() * pop.count());

                ind1 = pop.getIndividual(i1);
                ind2 = pop.getIndividual(i2);
        	}
                
            Individual newIndiv = crossover(ind1, ind2);
            if(newPopSelection == "Replacement")
            	newPopulation.saveIndividual(i, newIndiv);
            else if(newPopSelection == "Steady State") // Da testare, sembra ok, non ha senso partire da 0 perchè sono i migliori per def TODO
            {
            	newPopulation.saveIndividual(newPopulation.getWorstId(elitismOffset), newIndiv);
            }
            else
            	newPopulation.saveIndividual(i, newIndiv);
        }

        
        // Mutazioni random su tutti ma non sul migliore
        for (int i = elitismOffset; i < newPopulation.count(); i++) {
            mutate(newPopulation.getIndividual(i)); 
        }
        

        return newPopulation;
    }

    // Crossover -> Da cambiare, perchè qui posso cambiare i geni tra gli individui come voglio, ma nel
    // problema che abbiamo noi ogni gene può esistere una sola volta nell'individuo, dato che la questione è
    // solo come sono ordinati: non possono esserci duplicati. Sulle slide lui fa una cosa strana. TODO
    
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.count(); i++) {
            // Crossover
            if (Math.random() <= crossRate && Math.random() <= crossChance) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }

        if(debug)
        {
	        System.out.println("###");
	        System.out.println("  Parent 1 genes: "+ indiv1.toString());
	        System.out.println("  Parent 2 genes: "+ indiv2.toString());
	        System.out.println("  Crossover genes: "+ newSol.toString());
	        System.out.println("###");
        }
        return newSol;
    }

    // La mutation la farei come semplice scambio casuale di due locations tra due individui.. TODO
    private static void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.count(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                int gene = (int) Math.round(Math.random() * 20);
                indiv.setGene(i, gene);

                if(debug)
                {
	                System.out.println("###");
	                System.out.println("  Individual gene "+i+" Mutated in this generation! It has value "+indiv.getGene(i));
	                System.out.println("###");
                }
            }
        }
    }

    // Due gruppi di n individui, tra i due migliori di ciascuno viene poi effettuato il crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
    	Individual fittest;
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.count());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        fittest = tournament.getFittest();
        	
        return fittest;
    }
    
    // Viene scelto un individuo con probabilità proporzionale alla sua fitness rispetto agli altri.. se è
    // molto grande viene scelto con grande probabilità, etc
    private static Individual proportionalSelection(Population tournament) {
    	
        // Individuals with their chances
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
    	
        // Select random individual depending on its fitness
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
        return tournament.getIndividual(tournament.count()-1); //problemi con approssimazioni
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
