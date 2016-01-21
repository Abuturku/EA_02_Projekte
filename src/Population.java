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
        IPopulation newPop = new Population(new Chromosome[20]); //TODO VARIABLE MACHEN und populationsize muss durch diesen wert teilbar sein
        IChromosome[] evolvedChromosome =  new Chromosome[Configuration.instance.populationSize];


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


            IChromosome[] newChromosomes =  killBadChromosomes(newPop.getPopulation());
            newChromosomes = refillRandomChromosomes(newChromosomes);

            newChromosomes[newChromosomes.length-2] = children[0];
            newChromosomes[newChromosomes.length-1] = children[1];

            //System.out.println("Laenge vorher: "+ newPop.getPopulation().length +" | Laenge nachher: " + newChromosomes.length);

            for (int i = 0; i < newChromosomes.length;i++){
                evolvedChromosome[j+i] = newChromosomes[i];
            }
        }
        IPopulation evolvedPop = new Population(evolvedChromosome);
        return evolvedPop;
    }

    private IChromosome[] killBadChromosomes(IChromosome[] chromosomes){
        Arrays.sort(chromosomes);
        IChromosome[] newChromosomes = Arrays.copyOf(chromosomes, chromosomes.length/2);
        return newChromosomes;
    }

    private IChromosome[] refillRandomChromosomes(IChromosome[] chromosomes){
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
    public int getSumPopulationFitness() {

        int sumFitness = 0;
        for(IChromosome chromosome : population){
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
}
