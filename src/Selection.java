import Configuration.Configuration;
import Configuration.SelectionEnum;

/**
 * Created by Jan.Rissmann on 18.01.2016.
 */
public class Selection implements ISelection{


    public Chromosome[] topTenPercentOfPopulation(IPopulation population){


        return null;
    }

    public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType){
        IChromosome[] parents = new Chromosome[2];

        IPopulation partOfPopulationFather = new Population(new Chromosome[population.getPopulation().length/2]);
        IPopulation partOfPopulationMother = new Population(new Chromosome[population.getPopulation().length/2]);

        for(int i=0; i < population.getPopulation().length; i+=2){
            partOfPopulationFather.getPopulation()[i] = population.getPopulation()[i];
            partOfPopulationMother.getPopulation()[i] = population.getPopulation()[i+1];
        }

        switch (selectionType){
            case RouletteWheel:
                parents[0] = doRouletteWheelSelection(partOfPopulationFather);
                parents[1] = doRouletteWheelSelection(partOfPopulationMother);
                break;
            case Tournament:
                parents[0] = doTournamentSelection(partOfPopulationFather);
                parents[1] = doTournamentSelection(partOfPopulationMother);
                break;
        }

        return parents;
    }

    private IChromosome doRouletteWheelSelection(IPopulation partOfPopulation) {
        double count = 0.00001;
        int sumPopulationFitness = partOfPopulation.getSumPopulationFitness();

        double randomValue = Configuration.instance.randomGenerator.nextDouble(false, true);//0 ausgeschlossen 1 eingeschlossen

        for(IChromosome chromosome : partOfPopulation.getPopulation()){
            double selectionProbability = Math.round(chromosome.getFitness()/sumPopulationFitness * 100000) / 100000;
            Range range = new Range(count, count + selectionProbability);
            if (range.inRange(randomValue)) return chromosome;
            count+=selectionProbability;
        }

        return null;
    }

    private IChromosome doTournamentSelection(IPopulation population){
        return population.getFittest();
    }

}
