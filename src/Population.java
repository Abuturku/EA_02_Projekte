import Configuration.Configuration;

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
    public IPopulation evolve() {
        IPopulation newPop = new Population(new Chromosome[20]);
        IPopulation evolvedPop = new Population(new Chromosome[Configuration.instance.populationSize]);

        for(int j = 0; j < Configuration.instance.populationSize; j+= 20){
            for (int i = 0; i < 20; i++){
                newPop.getPopulation()[i] = this.population[j+i];
            }

            ISelection selection = new Selection();
            IChromosome[] parents = selection.getParents(newPop, Configuration.instance.selectionType);

            ICrossover co = new Crossover();
            IChromosome[] children = co.doCrossover(parents[0], parents[1]);

            if (Configuration.instance.randomGenerator.nextDouble() <= Configuration.instance.mutationRatio){
                IMutation mutation = new Mutation();
                children[0] = mutation.doMutation(children[0]);
            }
            if (Configuration.instance.randomGenerator.nextDouble() <= Configuration.instance.mutationRatio){
                IMutation mutation = new Mutation();
                children[1] = mutation.doMutation(children[1]);
            }


        }

        return evolvedPop;
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
