/**
 * Created by Linda on 18.01.2016.
 */
public interface IPopulation {

    public void evolve();
    //TODO Steps: Select - Crossover - Mutate
    //      generates a valid new generation

    public IChromosome getFittest();

    public IChromosome[] getPopulation();

    int getSumPopulationFitness();

}
