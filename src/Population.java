/**
 * Created by 9364290 on 18.01.16.
 */
public class Population implements IPopulation {

    private Chromosome[] actualPopulation;

    private Chromosome[] nextPopulation;

    Population(Chromosome[] actualPopulation){
        this.actualPopulation = actualPopulation;
        this.nextPopulation = new Chromosome[actualPopulation.length];
    }

    @Override
    public Chromosome[] getNextPopulation() {
        return nextPopulation;
    }

    @Override
    public void evolve() {

    }

    @Override
    public IChromosome getFittest() {
        return null;
    }

    @Override
    public Chromosome[] getActualPopulation() {
        return actualPopulation;
    }

    @Override
    public int getSumPopulationFitness() {
        return 0;
    }
}
