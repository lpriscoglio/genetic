public class GeneticAlg {

	
	/* GA parameters */
    private static final double crossRate = 0.5;
    //private static final double crossChance = 1;
    private static final double mutationRate = 0.1115;
    private static final int tournamentSize = 5; // 
    private static boolean elitism = false;
    private static int elitismCount = 1; 
    private static String selection = "Tournament"; //Tournament / Proportional / Random
    private static String newPopSelection = "Replacement"; //Replacement / Steady State
    private static final boolean debug = false; 
    private static final boolean debugMutation = false;
    private static final boolean debugCrossover = false;
    private static int elitismOffset = 0;
    public static int mutations = 0;
    private static int geneCount = 0;

    /* Public methods */
    
    // Evoluzione
    public static Population evolvePopulation(Population pop) {
        geneCount = pop.getIndividual(0).count();
        Population newPopulation = new Population(pop.count(), geneCount, false);
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
        for (int i = elitismOffset; i < pop.count(); i++) {
        	Individual ind1,ind2;
        	
        	if(selection == "Tournament")
        	{
        		ind1 = tournamentSelection(pop, geneCount);
        		ind2 = tournamentSelection(pop, geneCount);
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
                
            Individual newIndiv = crossover(ind1, ind2, geneCount);
            if(newPopSelection == "Replacement")
            	newPopulation.saveIndividual(i, newIndiv);
            else if(newPopSelection == "Steady State") // Da testare, sembra ok, non ha senso partire da 0 perchè sono i migliori per def TODO
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
            mutate(newPopulation.getIndividual(i), geneCount); 
        }

        return newPopulation;
    }

    // Crossover -> Scelta di un individuo base, poi scelgo per ognuno quale aggiungere e scambiare, e da quale genitore
    
    private static Individual crossover(Individual indiv1, Individual indiv2, int genes) {
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
    		
        /* Loop through genes
        for (int i = 0; i < genes; i++) {
            // Crossover
            if (Math.random() <= crossRate && Math.random() <= crossChance) {
                newSol.swapGenesByInsertion(i, indiv1.getGene(i));
            } else {
                newSol.swapGenesByInsertion(i, indiv2.getGene(i));
            }
        }
        if(debugCrossover)
        {
	        System.out.println("  Crossover genes: "+ newSol.toString());
	        System.out.println("###");
        }*/
        return newSol;
    }

    // Semplice scambio random di location in un individuo
    private static void mutate(Individual indiv, int genes) {
        // Loop through genes
        for (int i = 0; i < genes; i++) {
            if(debugMutation)
            {
                System.out.println("###");
                System.out.println("  Individual was "+indiv.toString());
            }
            if (Math.random() <= mutationRate) {
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
    private static Individual tournamentSelection(Population pop, int genes) {
        // Create a tournament population
    	Individual fittest;
        Population tournament = new Population(tournamentSize, genes, false);
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
        return tournament.getIndividual(tournament.count()-1); //approssimazioni
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
