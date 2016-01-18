import java.util.Arrays;

/**
 * Created by 9364290 on 18.01.16.
 */
public class Population implements IPopulation {

    private IChromosome[] population;

    Population(IChromosome[] population){
        this.population = population;
    }

    @Override
    public void evolve() {

    }

    @Override
    public IChromosome getFittest() {
        return null;
    }

    @Override
    public IChromosome[] getPopulation() {
        return population;
    }

    @Override
    public int getSumPopulationFitness() {
        return 0;
    }

    @Override
    public void sortPopulation() {
        Arrays.sort(population);
    }
}
