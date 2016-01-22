import Configuration.Configuration;
import Configuration.MersenneTwisterFast;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 9364290 on 18.01.16.
 */
public class Population implements IPopulation {

    static IChromosome[] evolvedChromosome =  new Chromosome[Configuration.INSTANCE.POPULATION_SIZE];

    private IChromosome[] population;

    Population(IChromosome[] population){
        this.population = population;
    }

    @Override
    public IPopulation evolve() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        IChromosome[] topTenChromosomes = topTenPercentOfPopulation(this);
        for (int i = 0; i < topTenChromosomes.length; i++){
            evolvedChromosome[i] = topTenChromosomes[i];
        }

        for(int j = getTenPercentOfTheLengthOfPopulation(); j < Configuration.INSTANCE.POPULATION_SIZE; j+= Configuration.INSTANCE.SIZE_OF_POPULATION_TO_FIND_PARENTS){
                    IPopulation partOfPopulationForOneParentPair = getPartOfPopulationForOneParentPair(j);


                    IChromosome[] parents = getParentsFromSelection(partOfPopulationForOneParentPair);

                    IChromosome[] children = getChildrenFromCrossoverTheParents(parents);

                    children[0] = mutateMaybeTheChromosome(children[0]);
                    children[1] = mutateMaybeTheChromosome(children[1]);

                    IChromosome[] newChromosomes =  killTheBadHalfOfChromosomes(partOfPopulationForOneParentPair.getPopulation());
                    newChromosomes = refillChromosomeArrayWithRandomChromosomes(newChromosomes);

                    newChromosomes[newChromosomes.length-2] = children[0];
                    newChromosomes[newChromosomes.length-1] = children[1];

                    //System.out.println("Laenge vorher: "+ newPop.getPopulation().length +" | Laenge nachher: " + newChromosomes.length);

                    for (int i = 0; i < newChromosomes.length;i++){
                        evolvedChromosome[j+i] = newChromosomes[i];
                    }
        }
        return new Population(evolvedChromosome);
    }

    private int getTenPercentOfTheLengthOfPopulation() {
        return getPopulation().length/10;
    }

    private IChromosome mutateMaybeTheChromosome(IChromosome children) {
        MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
        if (randomGenerator.nextDouble() <= Configuration.INSTANCE.MUTATION_RATIO){
            IMutation mutation = new Mutation();
            children = mutation.doMutation(children);
        }
        return children;
    }

    private IChromosome[] getChildrenFromCrossoverTheParents(IChromosome[] parents) {
        ICrossover co = new Crossover();
        return co.doCrossover(parents[0], parents[1]);
    }

    private IChromosome[] getParentsFromSelection(IPopulation partOfPopulationForOneParentPair) {
        ISelection selection = new Selection();
        return selection.getParents(partOfPopulationForOneParentPair, Configuration.INSTANCE.SELECTION_TYPE);
    }

    private IPopulation getPartOfPopulationForOneParentPair(int j) {
        IPopulation partOfPopulationForOneParentPair = new Population(new Chromosome[Configuration.INSTANCE.SIZE_OF_POPULATION_TO_FIND_PARENTS]);
        partOfPopulationForOneParentPair.setPopulation(Arrays.copyOfRange(this.getPopulation(), j, j + Configuration.INSTANCE.SIZE_OF_POPULATION_TO_FIND_PARENTS));
        return partOfPopulationForOneParentPair;
    }

    private IChromosome[] killTheBadHalfOfChromosomes(IChromosome[] chromosomes){
        Arrays.sort(chromosomes);
        IChromosome[] newChromosomes = Arrays.copyOf(chromosomes, chromosomes.length/2);
        return newChromosomes;
    }

    private IChromosome[] refillChromosomeArrayWithRandomChromosomes(IChromosome[] chromosomes){
        IChromosome[] newChromosomes = Arrays.copyOf(chromosomes,chromosomes.length*2);
        Arrays.fill(newChromosomes, chromosomes.length,newChromosomes.length, new Chromosome());
        return newChromosomes;
    }

    @Override
    public IChromosome getFittest() {
        IChromosome fittest = null;
        for(IChromosome chromosome : this.population){
            if(fittest == null || fittest.getFitness() < chromosome.getFitness()){
                fittest = chromosome;
            }
        }


        return fittest;
    }

    @Override
    public IChromosome[] getPopulation() {
        return population;
    }

    @Override
    public void setPopulation(IChromosome[] chromosomeArray){
        this.population = chromosomeArray;
    }

    @Override
    public int getSumPopulationFitness() {

        int sumFitness = 0;
        for(IChromosome chromosome : this.getPopulation()){
            sumFitness += chromosome.getFitness();
        }
        return sumFitness;
    }

    @Override
    public IPopulation sortPopulation() {
        Arrays.sort(population);
        return this;
    }

    @Override
    public IChromosome getLastChromosomeOfPopulation() {
        return this.getPopulation()[this.getPopulation().length-1];
    }

    public IChromosome[] topTenPercentOfPopulation(IPopulation population){
        return Arrays.copyOf(population.sortPopulation().getPopulation(), getTenPercentOfTheLengthOfPopulation());
    }
}
