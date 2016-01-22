
public interface IPopulation {

	public IPopulation evolve();
	// TODO Steps: Select - Crossover - Mutate
	// generates a valid new generation

	public IChromosome getFittest();

	public IChromosome[] getPopulation();

	void setPopulation(IChromosome[] chromosomeArray);

	int getSumPopulationFitness();

	public IPopulation sortPopulation();

	public IChromosome getLastChromosomeOfPopulation();
}
