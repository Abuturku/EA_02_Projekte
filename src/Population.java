import java.util.Arrays;

/**
 * Created by 9364290 on 18.01.16.
 */
public class Population implements IPopulation {

    private Chromosome[] population;

    Population(Chromosome[] population){
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
    public Chromosome[] getPopulation() {
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
