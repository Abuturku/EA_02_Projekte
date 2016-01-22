
public interface IChromosome extends Comparable<IChromosome> {

	public IChromosome generateRandomChromosome();

	public String getChromosome();

	public boolean isInPriceBudget();

	public int getFitness();

	public int compareTo(IChromosome chromosome);

}
