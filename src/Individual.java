
public class Individual {

	private int possibleGenes = 16;
	private int [] geneSequence = new int[possibleGenes];
	private int fitnessVal = 0;
	
	public Individual()
	{
		this.fitnessVal = 0;
		for (int i = 0; i < possibleGenes; i++) {
            int gene = (int) Math.round(Math.random() * 20); //Sarebbe meglio farla esterna.. ora restituisce solo uno 0-20
            geneSequence[i] = gene;
        }
	}
	
	public int getGene(int index)
	{
		return this.geneSequence[index];
	}
	
	public void setGene(int index, int value)
	{
		this.geneSequence[index] = value;
		this.fitnessVal = 0; //
	}
	
	public int count()
	{
		return this.possibleGenes;
	}
	
    public int getFitness() {
        if (this.fitnessVal == 0) {
            this.fitnessVal = Fitness.getFitness(this);
        }
        return this.fitnessVal;
    }
    
    @Override
    public String toString() {
        String report = "[ ";
        for (int i = 0; i < this.count(); i++) {
            report += getGene(i)+" , ";
        }
        return report+" ]";
    }
}
